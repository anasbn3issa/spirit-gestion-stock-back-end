package tn.esprit.spring.repositories;

import java.util.Set;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.DetailFacture;

@Repository
public interface DetailFactRepository extends CrudRepository<DetailFacture, Long> {

	@Query("SELECT df from DetailFacture df where df.facture.idFacture = ?1 AND df.produit.idProduit = ?2")
	Set<DetailFacture> getDetFactByFactureProduit(Long idFacture, Long idProduit);
	
	@Query("SELECT df FROM DetailFacture df WHERE df.facture.dateFacture BETWEEN :startDate AND :endDate AND df.produit.idProduit = :idProduit")
	List<DetailFacture> findDetailFacturesProduitDansIntervalle(@Param("startDate") Date
			startDate, @Param("endDate") Date endDate, @Param("idProduit") Long idProduit);


}
