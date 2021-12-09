package tn.esprit.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Reclamation;

@Repository
public interface ReclamationRepository extends CrudRepository<Reclamation, Long>{
	
	@Query(value="SELECT * FROM reclamation r WHERE r.id_produit = :idProduit" ,nativeQuery = true)
	List<Reclamation> getRecByProduitId(@Param("idProduit") Long idProduit );

}
