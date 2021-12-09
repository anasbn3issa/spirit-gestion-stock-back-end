package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import tn.esprit.spring.services.produit.PrdServiceImpl;

@RestController
@Api(tags = "Product management")
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProduitRestController {

	@Autowired
	PrdServiceImpl productService;
	
	@ApiOperation(value = "Récupérer la liste des produits")
	@GetMapping("/get-products")
	@ResponseBody
	public List<Produit> getProducts() {
	List<Produit> listProduits = productService.retrieveAllProduits();
	return listProduits;
	}
	
	@ApiOperation(value = "Récupérer un produit par Id")
	@GetMapping("/retrieve-product/{product-id}")
	@ResponseBody
	public Produit retrieveClient(@PathVariable("product-id") Long productId) {
	return productService.retrieveProduit(productId);
	}
	
	@ApiOperation(value = "Ajouter un nouveau produit")
	@PostMapping("/add-product/{idRayon}/{idStock}")
	@ResponseBody
	public Produit addProduct(@RequestBody Produit p, @PathVariable Long idRayon, @PathVariable Long idStock )
	{
		return productService.addProduit(p, idRayon, idStock);
	}
	
	@ApiOperation(value = "Supprimer un produit par Id")
	@DeleteMapping("/remove-product/{product-id}")
	@ResponseBody
	public void removeClient(@PathVariable("product-id") Long productId) {
		productService.deleteProduit(productId);
	}
	
	@ApiOperation(value = "Mettre a jour un produit")
	@PutMapping("/modify-product")
	@ResponseBody
	public Produit modifyProduct(@RequestBody Produit p) {
	return productService.updateProduit(p);
	}
	
}
