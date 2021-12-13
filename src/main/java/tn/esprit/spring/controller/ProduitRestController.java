package tn.esprit.spring.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import javax.servlet.http.HttpServletResponse;
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.services.produit.ExportToPDF;
import tn.esprit.spring.services.produit.PrdServiceImpl;
import org.springframework.ui.Model;
import java.util.Base64;

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
	@JsonIgnore
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
	
	//Pourcentage
	@PutMapping("/promotion/{id}/{pourcentage}")
	@ResponseBody
	public Produit promotion(@PathVariable("id") Long id, @PathVariable("pourcentage") int pourcentage) {
		Produit productpromotion =  (Produit) productService.retrieveProduit(id);
		float price = (float) productpromotion.getPrixUnitaire();
		float newprice= price*(100-pourcentage)/100;
		productpromotion.setPrixUnitaire(newprice);
		productpromotion.setPromotion(pourcentage);
		return this.productService.updateProduit(productpromotion);
		
	}
	
	@PutMapping("/annulerpromotion/{id}")
	@ResponseBody
	public Produit promotion(@PathVariable("id") Long id) {
		Produit productpromotion =  (Produit) productService.retrieveProduit(id);
		float price = (float) productpromotion.getPrixUnitaire();
		int poucentagepromotion = productpromotion.getPromotion();
		float oldprice=price+((poucentagepromotion*price)/100);
		productpromotion.setPrixUnitaire(oldprice);
		return this.productService.updateProduit(productpromotion);
		
		
	
	}
	
	

	
	//Print To PDF
	 @GetMapping("/export/pdf")
	    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=produit_" +  ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        List<Produit> listP = productService.retrieveAllProduits();
	         
	        ExportToPDF exporter = new ExportToPDF(listP);
	        exporter.export(response);
	         
	    }
	 
	 
	 
	 
	 
	
}
