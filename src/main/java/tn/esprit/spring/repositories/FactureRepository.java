package tn.esprit.spring.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.CategorieClient;

@Repository
public interface FactureRepository extends CrudRepository<Facture, Long> {
	
	@Query("SELECT f FROM Facture f WHERE f.dateFacture BETWEEN :startDate AND :endDate AND f.client.categorieClient = :categorieClient")
	List<Facture> retrieveFacturesDansIntervalleEtCategorieClient(@Param("startDate") Date
			startDate, @Param("endDate") Date endDate, @Param("categorieClient") CategorieClient categorieClient);
	
	@Query("SELECT f FROM Facture f WHERE f.dateFacture BETWEEN :startDate AND :endDate")
	List<Facture> retrieveFacturesDansIntervalle(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
//	@Query("SELECT f FROM Facture f ORDER BY f.dateFacture LIMIT 1")
//	Facture retrieveOldestFacture();
	
//	Facture findFirstByDateFacture();
	Facture findFirstByOrderByDateFactureAsc();

}
