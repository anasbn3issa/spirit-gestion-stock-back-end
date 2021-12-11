package tn.esprit.spring.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.entities.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
	
	@Query("SELECT p FROM Produit p WHERE p.stock.idStock <> ?1 ")
	List<Produit> existeAussi(Long idS);


}
