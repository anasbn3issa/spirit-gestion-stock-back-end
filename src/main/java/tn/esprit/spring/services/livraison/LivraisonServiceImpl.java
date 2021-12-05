package tn.esprit.spring.services.livraison;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import tn.esprit.spring.entities.Livraison;

public interface LivraisonServiceImpl {
	
	List<Livraison> retrieveAllLivraisons();
	
	Livraison addLivraison(Livraison l, Long factureId, Long LivreurId);

	void deleteLivraison(Long id);

	Livraison updateLivraison(Livraison l);

	Livraison retrieveLivraison(Long id);
	
	List<Object[]> getLivraisonsCountbyLivId(Long id);	
}
