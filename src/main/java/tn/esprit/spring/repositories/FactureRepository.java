package tn.esprit.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Facture;

@Repository
public interface FactureRepository extends CrudRepository<Facture, Long> {
	
	void cancelFacture(Long id);

}
