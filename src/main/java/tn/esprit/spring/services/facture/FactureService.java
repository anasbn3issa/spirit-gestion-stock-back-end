package tn.esprit.spring.services.facture;

import java.util.Date;
import java.util.List;

import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.CategorieClient;


public interface FactureService {
	
	List<Facture> retrieveAllFacture();

	Facture addFacture(Facture f, Long idClient);

	void deleteFacture(Long id);

	Facture updateFacture(Facture f);

	Facture retrieveFacture(Long id);
	
	void cancelFacture(Long id);
	
	float getChiffreAffaireParCategorieClient(CategorieClient categorieClient, Date startDate, Date endDate);
	
	float getProgressionEntreprise(Date startDate, Date endDate);
	
	void getProgressionEntrepriseCreationToPresent();

}
