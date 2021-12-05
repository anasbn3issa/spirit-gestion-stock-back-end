package tn.esprit.spring.repositories;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Livraison;

@Repository
public interface LivraisonRepository extends CrudRepository<Livraison, Long> {
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
}