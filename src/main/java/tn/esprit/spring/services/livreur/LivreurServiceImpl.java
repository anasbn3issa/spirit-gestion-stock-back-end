package tn.esprit.spring.services.livreur;

import java.util.HashMap;
import java.util.List;

import tn.esprit.spring.entities.Livreur;

public interface LivreurServiceImpl {
	
	HashMap<String,Object> livreurspagination(Integer pageNo, Integer pageSize);

	List<Livreur> retrieveAllLivreurs();
	
	Livreur addLivreur(Livreur l);

	void deleteLivreur(Long id);

	Livreur updateLivreur(Livreur l);

	Livreur retrieveLivreur(Long id);
	
	HashMap<String,Object> disableLivreursWithIds(List<Long> ids);
}
