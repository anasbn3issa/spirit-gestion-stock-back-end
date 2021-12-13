package tn.esprit.spring.services.facture;

import java.util.Date;
import java.util.List;

import tn.esprit.spring.entities.CategorieClient;
import tn.esprit.spring.entities.Facture;


public interface FactureRepositoryCustom {
	
	List<Facture> retrieveAllFacture();

	Facture addFacture(Facture f, Long idClient);

	void deleteFacture(Long id);

	Facture updateFacture(Facture f);

	Facture retrieveFacture(Long id);
	
	void cancelFacture(Long id);
	
	List<Facture> retrieveFacturesByDates(Date date1, Date date2);
	
	List<Facture> getFacturesByClient(Long idClient);
	
	float getRevenuBrutProduit(Long idProduit, Date startDate, Date endDate);
	
	List<Facture> getOpenFactures();

	float getChiffreAffaireParCategorieClient(CategorieClient categorieClient, Date startDate, Date endDate);
	
	float getProgressionEntreprise(Date startDate, Date endDate);
	
	void getProgressionEntrepriseCreationToPresent();

}
