package tn.esprit.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Produit;

@Repository
public interface ProduitRepository extends CrudRepository<Produit, Long> {
	
}
