package tn.esprit.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.DetailProduit;

@Repository
public interface DetailProdRepository extends CrudRepository<DetailProduit, Long> {

}
