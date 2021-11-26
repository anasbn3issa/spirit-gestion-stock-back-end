package tn.esprit.spring.services.livreur;

import java.util.List;

import tn.esprit.spring.entities.Livreur;

public interface LivreurServiceImpl {
	
	List<Livreur> retrieveAllLivreurs();
	
	Livreur addLivreur(Livreur l);

	void deleteLivreur(Long id);

	Livreur updateLivreur(Livreur l);

	Livreur retrieveLivreur(Long id);
}
