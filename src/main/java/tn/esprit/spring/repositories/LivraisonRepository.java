package tn.esprit.spring.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Livraison;

@Repository
public interface LivraisonRepository extends CrudRepository<Livraison, Long> {
}
