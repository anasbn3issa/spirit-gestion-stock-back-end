package tn.esprit.spring.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.CategorieClient;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.DetailFacture;
import tn.esprit.spring.entities.Produit;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
	@Query("SELECT c FROM Client c WHERE c.dateNaissance BETWEEN ?1 AND ?2")
	List<Client> retrieveClientsByDates(Date date1, Date date2);
	
	@Query("SELECT SUM (f.montantFacture) FROM Client c, Facture f WHERE (c.idClient = :idClient) AND (f.client.idClient = :idClient)")
	float getIncomeFromClient(@Param("idClient")Long idClient);
	
	@Query("SELECT f FROM Client c, DetailFacture f WHERE (c.idClient = :idClient) AND (f.facture.client.idClient = :idClient)")
	List<DetailFacture> getPurchaseHistory(@Param("idClient") Long idClient);

	@Transactional
	@Modifying
	@Query("update Client c set c.categorieClient = :categorie where c.idClient= :idClient")
	int updateCategorieClient(@Param("idClient") Long idClient,@Param("categorie") CategorieClient categorieClient);
}
