package tn.esprit.spring.services.facture;

import java.util.List;

import tn.esprit.spring.entities.Facture;


public interface FactureRepositoryCustom {
	
	List<Facture> retrieveAllFacture();

	Facture addFacture(Facture f);

	void deleteFacture(Long id);

	Facture updateFacture(Facture f);

	Facture retrieveFacture(Long id);
	
	void cancelFacture(Long id);

}
