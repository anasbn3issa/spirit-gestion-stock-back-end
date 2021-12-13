package tn.esprit.spring.services.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import tn.esprit.spring.config.LoggingAspect;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.entities.Stock;
import tn.esprit.spring.repositories.ProduitRepository;
import tn.esprit.spring.repositories.StockRepository;
import tn.esprit.spring.services.produit.ProduitServiceImpl;

@Service
public class StkServiceImpl implements StockServiceImpl{
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private ProduitServiceImpl prodservc;
	
	//CRUD
	@Override
	public List<Stock> retrieveAllStock() {
		return (List<Stock>) stockRepository.findAll();
	}

	@Override
	public Stock addStock(Stock s) {
		return stockRepository.save(s);
	}

	@Override
	public void deleteStock(Long id) {
		List<Produit> prods = this.stockproducts(id);
		
		//Rendre les produits desormais dispos.
		for (Produit p : prods) {
			p.setStock(null);
			prodservc.updateProduit(p);
			prodservc.addProduit(p, p.getRayon().getIdRayon(), null);

			}
		stockRepository.delete(this.retrieveStock(id));
	}

	@Override
	public Stock updateStock(Stock s) {
		return stockRepository.save(s);
	}

	@Override
	public Stock retrieveStock(Long id) {
		return stockRepository.findById(id).orElse(null);
	}

	@Override
	public List<Produit> stockproducts(Long ids) {
		
		Stock s = this.retrieveStock(ids);
		Set<Produit> set = s.getProduits();
		List<Produit> convertList = new ArrayList<Produit>();
		for (Produit p : set) {
			convertList.add(p);
			}
		
		return convertList;
		
	}
	
	//MANIPULATION PRODUITS DES STOCKS

	@Override
	public Set<Produit> assignProduitToStock(Long idProduit, Long idStock) {
		Produit p = prodservc.retrieveProduit(idProduit);
		Stock s = this.retrieveStock(idStock);
		

		p.setStock(s);
		prodservc.updateProduit(p);
		
		Set<Produit> produits= s.getProduits();
		produits.add(p);
		s.setProduits(produits);
		s.setQte(s.getQte()+1);
		this.updateStock(s);
		
		return produits;
	}

	@Override
	public void deleteProductFromStock(Long idProduit, Long idStock) {
		Produit p = prodservc.retrieveProduit(idProduit);
		Stock s = this.retrieveStock(idStock);
		

		p.setStock(null);
		prodservc.updateProduit(p);
		
		Set<Produit> produits= s.getProduits();
		produits.remove(p);
		s.setProduits(produits);
		s.setQte(s.getQte()-1);
		this.updateStock(s);
	
	}
	
	@Override
	public List<Produit> prodNonAssig(Long idS) {
		List<Produit> list = new ArrayList<Produit>() ;
		List<Produit> allprods = prodservc.retrieveAllProduits();
		for (Produit p : allprods) {
			if (p.getStock() == null)
				{ list.add(p);}
		}
		return list;
	}

	@Override
	public List<Produit> existeAussi(Long ids) {

		return stockRepository.existeAussi(ids);

	}
	
	// CHIFFRES 
	
	@Override
	public float valeurStock(Long idS) {
		
		float valeur= 0f;
		
		//Recup stock
		Stock s = this.retrieveStock(idS);	
		//Parcous + calcul
		for (Produit p : s.getProduits()) {
			valeur = valeur + p.getPrixUnitaire();
			}
		
		return valeur;

	}

	@Override
	public float tauxTVA(Long idS) {
		float valeurpropre = this.valeurStock(idS);
		return (valeurpropre * 19) /100;
	}

	@Override
	public float valeurStockSansTva(Long idS) {
		return (this.valeurStock(idS)-this.tauxTVA(idS));
	}
	
	
	//BILAN MENSUEL
	//@Scheduled(cron = "0 0 1 * * *" )
	@Scheduled(cron = "*/10 * * * * *" )
	@Override
	public Stock StockPlusRentable() {
		List <Stock> list = this.retrieveAllStock();
		Stock s =list.get(0);

		for (Stock s1 : list) {
		if (this.valeurStockSansTva(s1.getIdStock()) > this.valeurStockSansTva(s.getIdStock()))
				{	
					s = s1;
				}
			}
		

		return s;
	}
	
	@Scheduled(cron = "0 0 1 * * *" )
	@Override
	public Stock moinsRentable() {
		List <Stock> list = this.retrieveAllStock();
		Stock s =list.get(0);

		for (Stock s1 : list) {
			if (this.valeurStockSansTva(s1.getIdStock()) < this.valeurStockSansTva(s.getIdStock()))
				{	
					s = s1;
				}
			}
		
		return s;
	}

	@Override
	public boolean StockEquilibre(Long ids) {
				
		return (this.retrieveStock(ids)).getQte()>this.retrieveStock(ids).getQteMin();
	}
	
	//STATISTIQUES

	@Override
	public float pourcentageAlimentaire() {

		int totalalimentaire = 0;
		int totalproduits = 0;
		
		List <Stock> list = this.retrieveAllStock();
		
		for (Stock s1 : list) {
			Set<Produit> produits= s1.getProduits();
			totalproduits = totalproduits + produits.size();

			for (Produit p : produits) {
				if ((p.getDetailProduit().getCategorieProduit()).toString().equals("Alimentaire"))
					{ totalalimentaire = totalalimentaire+1;}
			}
		}
		
		return (totalalimentaire * 100 / totalproduits );

	}

	@Override
	public float pourcentageQuicaillerie() {
		float totalquincaillerie = 0f;
		float totalproduits = 0f;

		List <Stock> list = this.retrieveAllStock();
		
		for (Stock s1 : list) {
			Set<Produit> produits= s1.getProduits();
			totalproduits = totalproduits + produits.size();
			
			for (Produit p : produits) {
				if ((p.getDetailProduit().getCategorieProduit()).toString().equals("Quincaillerie"))
					{ totalquincaillerie = totalquincaillerie+1;}
			}
		}
		
		return (totalquincaillerie * 100 / totalproduits );
	}

	@Override
	public float pourcentageElectromenager() {

		float totalelectromenager = 0f;
		float totalproduits = 0f;
		List <Stock> list = this.retrieveAllStock();
		
		for (Stock s1 : list) {
			Set<Produit> produits= s1.getProduits();
			totalproduits = totalproduits + produits.size();
			for (Produit p : produits) {
				if ((p.getDetailProduit().getCategorieProduit()).toString().equals("Electromenager"))
					{ totalelectromenager = totalelectromenager+1; }
			}
		}
		return (totalelectromenager * 100 / totalproduits );
	}

	
	

	


	
}
