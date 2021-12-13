package tn.esprit.spring.repositories;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Livraison;
import tn.esprit.spring.entities.Livreur;

@Repository
public interface LivraisonRepository extends PagingAndSortingRepository<Livraison, Long> {
	/*@Query("SELECT "
			+ "count(CASE WHEN  liv.etat ='Nouveau' THEN 1 END) as countNew,"
			+ "count(CASE WHEN  liv.etat ='EnTransition' THEN 1 END) as countInTrans,"
			+ "count(CASE WHEN  liv.etat ='Livre' THEN 1 END) as countLivre "
			+ "FROM Livraison liv "
			+ "WHERE liv.livreur.idLivreur = ?1")*/
	@Query("SELECT liv.etat, count(liv.idLivraison) "
			+ "FROM Livraison liv "
			+ "WHERE liv.livreur.idLivreur = ?1 "
			+ "GROUP BY liv.etat")
	List<Object[]> getLivraisonsCountbyLivId(Long id);
	
	@Query("SELECT liv "
			+ "FROM Livraison liv "
			+ "WHERE liv.livreur.idLivreur = ?1 ")
	List<Livraison> getLivraisonsbyLivId(Long id);
	
	@Query("SELECT count(l.idLivraison) FROM Livraison l")
	Long retrieveLivraisonCount();
	
	@Query("SELECT l FROM Livraison l WHERE "
			+ "lower(l.code) LIKE lower(concat('%', ?1,'%')) "
			+ "OR lower(l.livreur.nom) LIKE lower(concat('%', ?1,'%')) "
			+ "OR lower(concat(l.facture.client.prenom,l.facture.client.nom)) LIKE lower(concat('%', ?1,'%')) "
			+ "OR lower(l.etat) LIKE lower(concat('%', ?1,'%')) "
			+ "OR l.dateLivraisonPrevue LIKE %?1%"
			+ "OR l.dateLivraison LIKE %?1%")
	Page<Livraison> filterLivraisonList(String filter, Pageable pageable);
	

	@Query(value = "SELECT MAX(l.id_livraison) FROM livraison l", 
			  nativeQuery = true)
	Long getLastInsertedId();
}