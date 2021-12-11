package tn.esprit.spring.services.produit;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.DetailProduit;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.repositories.ProduitRepository;
import tn.esprit.spring.services.detailProd.DetProdServiceImpl;
import tn.esprit.spring.services.rayon.RynServiceImpl;
import tn.esprit.spring.services.stock.StkServiceImpl;

@Service
public class PrdServiceImpl implements ProduitServiceImpl{
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	private RynServiceImpl rynServiceImpl;
	
	@Autowired
	private StkServiceImpl stkServiceImpl;
	
	@Autowired
	private DetProdServiceImpl detProdServiceImpl;

	@Override
	public List<Produit> retrieveAllProduits() {
		return (List<Produit>) produitRepository.findAll();
	}
	
	@Transactional
	@Override
	public Produit addProduit(Produit p, Long idRayon, Long idStock) {
		p.setRayon(rynServiceImpl.retrieveRayon(idRayon));
		p.setStock(stkServiceImpl.retrieveStock(idStock));
		
		Date date = new Date(System.currentTimeMillis());

		DetailProduit dp = p.getDetailProduit();

		dp.setDateCreation(new Date());

		dp.setDateDernièreModification(date);
		

		dp.setProduit(p);
		detProdServiceImpl.addProdDetail(dp);
		return produitRepository.save(p);
	}

	@Override
	public Produit retrieveProduit(Long id) {
		return produitRepository.findById(id).orElse(null);

	}
	@Override
	public Produit updateProduit(Produit p) {
		
		DetailProduit dp = p.getDetailProduit();
		Date date = new Date(System.currentTimeMillis());
		dp.setDateDernièreModification(date);
		p.setDetailProduit(dp);
		p.getDetailProduit().setCategorieProduit(p.getDetailProduit().getCategorieProduit());
		return produitRepository.save(p);
	}

	@Override
	public void deleteProduit(Long id) {
		produitRepository.deleteById(id);
	}
	
	

}
