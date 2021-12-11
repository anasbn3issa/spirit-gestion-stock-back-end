package tn.esprit.spring.services.stock;

import java.util.List;
import java.util.Set;

import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.entities.Stock;


public interface StockServiceImpl {
	
	List<Stock> retrieveAllStock();

	Stock addStock(Stock c);

	void deleteStock(Long id);

	Stock updateStock(Stock c);

	Stock retrieveStock(Long id);
	
	Set<Produit> assignProduitToStock(Long idProduit, Long idStock);
	
	void deleteProductFromStock(Long idProduit, Long idStock);

	
	
	//produit qu on peut assigner
	List<Produit> prodNonAssig(Long idS);
	//produits assignés
	List<Produit> stockproducts (Long ids);

	//produits existants aussi!
	List<Produit> existeAussi (Long ids);

	//chiffres stock
	float valeurStock (Long idS);
	float tauxTVA (Long idS);
	float valeurStockSansTva (Long idS);
	
	//chaque mois
	Stock StockPlusRentable ();
	Stock moinsRentable ();
	
	boolean StockEquilibre(Long ids);
	
	//statistiques totales
	float pourcentageAlimentaire();
	float pourcentageQuicaillerie();
	float pourcentageElectromenager();

	
	
	

	
	

	
	
}
