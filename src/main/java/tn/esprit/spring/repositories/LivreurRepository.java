package tn.esprit.spring.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Livraison;
import tn.esprit.spring.entities.Livreur;

@Repository
public interface LivreurRepository extends CrudRepository<Livreur, Long> {
}
