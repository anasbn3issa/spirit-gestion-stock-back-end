package tn.esprit.spring.services.reclamation;

import java.util.HashMap;
import java.util.List;

import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.entities.Reclamation;

public interface ReclamationServiceImpl {
	List<Reclamation> retrieveAllReclamations();

	Reclamation addReclamation(Reclamation r, Long idProduit );

	Reclamation retrieveReclamation(Long id);
		
	void deleteReclamation(Long id);
	
	List<Reclamation> retrieveRecByProduitId(Long idProduit);

	HashMap<String, Integer> Claimmax();
	
	Produit WorstProduct();
}
