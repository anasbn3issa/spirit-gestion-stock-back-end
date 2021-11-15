package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Stock;
import tn.esprit.spring.services.stock.StkServiceImpl;

@RestController
@Api(tags = "Stock management")
@RequestMapping("/stock")
public class StockRestController {
	@Autowired
	StkServiceImpl stockService;
	
	@ApiOperation(value = "Récupérer la liste des stocks")
	@GetMapping("/retrieve-all-stocks")
	@ResponseBody
	public List<Stock> getstocks() {
		return stockService.retrieveAllStock();
	}
	
	@ApiOperation(value = "Récupérer un stock par Id")
	@GetMapping("/retrieve-stock/{stock-id}")
	@ResponseBody
	public Stock retrieveStock(@PathVariable("stock-id") Long stockid) {
	return stockService.retrieveStock(stockid);
	}
	
	@ApiOperation(value = "Ajouter un nouveau stock")
	@PostMapping("/add-stock")
	@ResponseBody
	public Stock addStock(@RequestBody Stock s)
	{
		return stockService.addStock(s);
	}
	
	@ApiOperation(value = "Supprimer un stock par Id")
	@DeleteMapping("/remove-stock/{stock-id}")
	@ResponseBody
	public void removeStock(@PathVariable("stock-id") Long stockId) {
		stockService.deleteStock(stockId);
	}
	
	@ApiOperation(value = "Mettre a jour un stock")
	@PutMapping("/modify-stock")
	@ResponseBody
	public Stock modifyStock(@RequestBody Stock stock) {
	return stockService.updateStock(stock);
	}

}
