package tn.esprit.spring.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.CategorieClient;
import tn.esprit.spring.entities.Facture;

@Repository
public interface FactureRepository extends CrudRepository<Facture, Long> {
	
	@Query("SELECT f FROM Facture f WHERE f.dateFacture BETWEEN :startDate AND :endDate AND f.client.categorieClient = :categorieClient")
	List<Facture> retrieveFacturesDansIntervalleEtCategorieClient(@Param("startDate") Date
			startDate, @Param("endDate") Date endDate, @Param("categorieClient") CategorieClient categorieClient);
	
	@Query("SELECT f FROM Facture f WHERE f.dateFacture BETWEEN ?1 AND ?2")
	List<Facture> retrieveFacturesByDates(Date date1, Date date2);
	
	@Query("SELECT f FROM Facture f WHERE f.client.idClient = ?1")
	List<Facture> getFacturesByClient(Long idClient);
	
	@Query("SELECT SUM(f.montantFacture) FROM Facture f WHERE f.client.categorieClient = ?1 AND (f.dateFacture BETWEEN ?2 AND ?3)")
	public float getChiffreAffaireParCategorieClient(CategorieClient categorieClient, Date startDate, Date endDate);
	
	@Query("SELECT f FROM Facture f WHERE f.active=true AND f.etat= 'Non_payee'")
	List<Facture> getActiveFactures();

	@Query("SELECT f FROM Facture f WHERE f.dateFacture BETWEEN :startDate AND :endDate")
	List<Facture> retrieveFacturesDansIntervalle(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	Facture findFirstByOrderByDateFactureAsc();
}
