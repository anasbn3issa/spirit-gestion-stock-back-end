package tn.esprit.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.DetailProduit;

@Repository
public interface ProdDetailRepository extends CrudRepository<DetailProduit, Long> {

}
