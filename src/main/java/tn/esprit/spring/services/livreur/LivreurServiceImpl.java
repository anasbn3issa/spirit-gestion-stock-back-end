package tn.esprit.spring.services.livreur;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tn.esprit.spring.entities.Livreur;

public interface LivreurServiceImpl {
	
	HashMap<String,Object> livreurspagination(Integer pageNo, Integer pageSize, String filter);

	List<Livreur> retrieveAllLivreurs();
	
	List<Livreur> retrieveActiveLivreurs();
	
	Livreur addLivreur(Livreur l);

	void deleteLivreur(Long id);

	Livreur updateLivreur(Livreur l);

	Livreur retrieveLivreur(Long id);
	
	HashMap<String,Object> disableLivreursWithIds(List<Long> ids);
	
	Page<Livreur> filterLivreurList(String filter, Pageable pageable);
}
