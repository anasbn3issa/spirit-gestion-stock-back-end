package tn.esprit.spring.services.stock;

import java.util.List;

import tn.esprit.spring.entities.Stock;


public interface StockServiceImpl {
	
	List<Stock> retrieveAllStock();

	Stock addStock(Stock c);

	void deleteStock(Long id);

	Stock updateStock(Stock c);

	Stock retrieveStock(Long id);

}
