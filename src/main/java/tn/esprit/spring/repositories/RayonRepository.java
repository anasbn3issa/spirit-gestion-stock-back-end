package tn.esprit.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Rayon;

@Repository
public interface RayonRepository extends CrudRepository<Rayon, Long> {

}
