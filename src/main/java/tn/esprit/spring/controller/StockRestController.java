package tn.esprit.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.entities.Stock;
import tn.esprit.spring.services.stock.StkServiceImpl;
import tn.esprit.spring.services.stock.StockServiceImpl;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(tags = "Stock management")
@RequestMapping("/stock")
public class StockRestController {
	@Autowired
	StockServiceImpl stockService;
	
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

	@PutMapping("/assign-prod/{stock-id}/{id-prod}")
	@ResponseBody
	public void assignProd(@PathVariable("stock-id") Long stockId,@PathVariable("id-prod") Long productId)
	{
	 stockService.assignProduitToStock(productId, stockId);
	}

	@PutMapping("/delete-prod/{stock-id}/{id-prod}")
	@ResponseBody
	public void deleteProd(@PathVariable("stock-id") Long stockId,@PathVariable("id-prod") Long productId)
	{
	 stockService.deleteProductFromStock(productId, stockId);
	}
	
	@ApiOperation(value = "Récupérer la liste des produits assignés")
	@GetMapping("/retrieve-products/{stock-id}")
	@ResponseBody
	public List<Produit> stockproducts(@PathVariable("stock-id")Long ids)
	{
		return stockService.stockproducts(ids);

	}
	
	
	
	@ApiOperation(value = "Récupérer la valeur du stock en question")
	@GetMapping("/valeurStock/{stock-id}")
	@ResponseBody
	public float valeurStock(@PathVariable("stock-id")Long ids)
	{
		return stockService.valeurStock(ids);
	}
	

	@ApiOperation(value = "Calcul du taux de TVA")
	@GetMapping("/tauxTVA/{stock-id}")
	@ResponseBody
	public float tauxTVA(@PathVariable("stock-id")Long ids)
	{
		return stockService.tauxTVA(ids);
	}
	
	@ApiOperation(value = "Calcul valeur stock sans TVA")
	@GetMapping("/valeurSansTva/{stock-id}")
	@ResponseBody
	public float valeurPropre(@PathVariable("stock-id")Long ids)
	{
		return stockService.valeurStockSansTva(ids);
	}
	
	@ApiOperation(value = "Stock plus rentable")
	@GetMapping("/stockplusrentable")
	@ResponseBody
	public Stock StockPlusRentable()
	{
		return stockService.StockPlusRentable();
	}
	
	@ApiOperation(value = "Stock moins rentable")
	@GetMapping("/stockmoinsrentable")
	@ResponseBody
	public Stock StockMoinsRentable()
	{
		return stockService.moinsRentable();
	}
	
	@ApiOperation(value = "Stock Equilibre")
	@GetMapping("/stockequilibre/{stock-id}")
	@ResponseBody
	public boolean StockEquilibre(@PathVariable("stock-id")Long ids)
	{
		return stockService.StockEquilibre(ids);
	}
	

	@ApiOperation(value = "Pourcentage Alimentaire")
	@GetMapping("/pourcentageAlimentaire")
	@ResponseBody
	public float poucentageAlimentaire()
	{
		return stockService.pourcentageAlimentaire();
	}
	
	@ApiOperation(value = "Pourcentage Electromenager")
	@GetMapping("/pourcentageElectromenager")
	@ResponseBody
	public float poucentageElectromenager()
	{
		return stockService.pourcentageElectromenager();
	}
	
	@ApiOperation(value = "Pourcentage Quincaillerie")
	@GetMapping("/pourcentageQuicaillerie")
	@ResponseBody
	public float poucentageQuincaillerie()
	{
		return stockService.pourcentageQuicaillerie();
	}
	

	@ApiOperation(value = "Produits non assignés")
	@GetMapping("/prodNonAssig/{stock-id}")
	@ResponseBody
	public List<Produit> prodNonAss(@PathVariable("stock-id")Long idS)
	{
		return stockService.prodNonAssig(idS);
	}

	@ApiOperation(value = "Produits assignés a d'autres stocks")
	@GetMapping("/existaussi/{stock-id}")
	@ResponseBody
	public List<Produit> existeAussi(@PathVariable("stock-id")Long idS)
	{
		return stockService.existeAussi(idS);
	}
	
	


}
