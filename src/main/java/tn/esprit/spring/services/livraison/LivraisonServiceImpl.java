package tn.esprit.spring.services.livraison;

import java.util.HashMap;
import java.util.List;

import tn.esprit.spring.entities.Livraison;

public interface LivraisonServiceImpl {
	
	List<Livraison> retrieveAllLivraisons();
	
	Livraison addLivraison(Long factureId, Long LivreurId);

	HashMap<String,Object> deleteLivraison(Long id);
	
	Livraison updateLivraison(Livraison l);

	Livraison retrieveLivraison(Long id);
	
	List<Object[]> getLivraisonsCountbyLivId(Long id);
	
	HashMap<String,Object> livraisonspagination(Integer pageNo, Integer pageSize, String filter);
}
