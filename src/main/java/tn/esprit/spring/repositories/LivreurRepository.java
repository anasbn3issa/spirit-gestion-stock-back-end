package tn.esprit.spring.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Livraison;
import tn.esprit.spring.entities.Livreur;

@Repository
public interface LivreurRepository extends PagingAndSortingRepository<Livreur, Long> {
	
	@Query("SELECT count(l.idLivreur) FROM Livreur l")
	Long retrieveLvreursCount();
	
	@Query("SELECT l FROM Livreur l where l.etat='Actif'")
	List<Livreur> retrieveActiveLivreurs();
	
	@Query("SELECT l FROM Livreur l WHERE "
			+ "lower(l.code) LIKE lower(concat('%', ?1,'%')) "
			+ "OR lower(l.nom) LIKE lower(concat('%', ?1,'%')) "
			+ "OR lower(l.email) LIKE lower(concat('%', ?1,'%')) "
			+ "OR l.telephone LIKE %?1% "
			+ "OR lower(l.addresse) LIKE lower(concat('%', ?1,'%')) "
			+ "OR lower(l.etat) LIKE lower(concat('%', ?1,'%')) ")
	Page<Livreur> filterLivreurList(String filter, Pageable pageable);
	
	
	
	@Modifying
	@Query("UPDATE Livreur l set l.etat='Suspendu' where l.idLivreur in ?1")
	void disableLivreursWithIds(List<Long> ids);
}
