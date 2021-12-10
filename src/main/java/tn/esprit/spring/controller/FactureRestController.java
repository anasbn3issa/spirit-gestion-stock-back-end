package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.spring.entities.CategorieClient;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.DetailFacture;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.services.client.CltServiceImpl;
import tn.esprit.spring.services.detailFact.DtFctServiceImpl;
import tn.esprit.spring.services.facture.FactureRepositoryImpl;

@RestController
@Api(tags = "Facture management")
@RequestMapping("/facture")
public class FactureRestController {

	@Autowired
	FactureRepositoryImpl factureService;
	
	@Autowired
	DtFctServiceImpl factureDetService;
	
	@ApiOperation(value = "Récupérer la liste des factures")
	@GetMapping("/retrieve-all-factures")
	@ResponseBody
	public List<Facture> getFactures() {
	return factureService.retrieveAllFacture();
	}
	
	@ApiOperation(value = "Récupérer la liste des factures actives")
	@GetMapping("/retrieve-open-factures")
	@ResponseBody
	public List<Facture> getOpenFactures() {
	return factureService.getOpenFactures();
	}
	
	@ApiOperation(value = "Récupérer une facture par Id")
	@GetMapping("/retrieve-facture/{facture-id}")
	@ResponseBody
	public Facture retrieveFacture(@PathVariable("facture-id") Long factureId) {
	return factureService.retrieveFacture(factureId);
	}
	
	@ApiOperation(value = "Ajouter une nouvelle facture")
	@PostMapping("/add-facture/{client-id}")
	@ResponseBody
	public Facture addFacture(@RequestBody Facture f, @PathVariable("client-id") Long clientId)
	{		
		return factureService.addFacture(f, clientId);
	}
	
	@ApiOperation(value = "Supprimer une facture par Id")
	@DeleteMapping("/remove-facture/{facture-id}")
	@ResponseBody
	public void removeFacture(@PathVariable("facture-id") Long factureId) {
	factureService.deleteFacture(factureId);
	}
	
	@ApiOperation(value = "Annuler une facture par Id")
	@PutMapping("/cancel-facture/{facture-id}")
	@ResponseBody
	public void cancelFacture(@PathVariable("facture-id") Long factureId) {
	factureService.cancelFacture(factureId);
	}
	
	@ApiOperation(value = "Récupérer la liste des factures par dates")
	@GetMapping("/retrieve-factures-by-dates")
	@ResponseBody
	public List<Facture> retrieveFacturesByDates(@RequestParam Map<String, Date> dates) {

	return factureService.retrieveFacturesByDates(dates.get("dateDeb"), dates.get("dateFin"));
	}
	
	@ApiOperation(value = "Récupérer la liste des factures")
	@GetMapping("/retrieve-all-factures-by-client/{client-id}")
	@ResponseBody
	public List<Facture> getFacturesByClient(@PathVariable("client-id") Long clientId) {
	return factureService.getFacturesByClient(clientId);
	}
	
	@ApiOperation(value = "Récupérer la liste des factures par dates pour une catégorie client par dates")
	@GetMapping("/retrieve-factures-by-dates-categ-client/{categ}")
	@ResponseBody
	public float getChiffreAffaireParCategorieClient(@PathVariable("categ") CategorieClient categClt, @RequestParam Map<String, Date> dates) {
	return factureService.getChiffreAffaireParCategorieClient(categClt, dates.get("dateDeb"), dates.get("dateFin"));
	}
	
	@ApiOperation(value = "calculer le revenu brut généré par un produit entre deux dates.")
	@GetMapping("/calculer-revenue-by-produit-dates/{product-id}")
	@ResponseBody
	public float getRevenuBrutProduit(@PathVariable("product-id") Long produitId, @RequestParam Map<String, Date> dates) {
	return factureService.getRevenuBrutProduit(produitId, dates.get("dateDeb"), dates.get("dateFin"));
	}
	
}
