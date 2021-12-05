package tn.esprit.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.dto.CheckProgressDTO;
import tn.esprit.spring.dto.ChiffreAffaireParCategorieClientDTO;
import tn.esprit.spring.dto.RevenuGenereProduitDTO;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.services.detailFact.DetFactServiceImpl;
import tn.esprit.spring.services.facture.FactureService;

@RestController
@RequestMapping("/facture")
public class FactureRestController {
	@Autowired
	private FactureService factureService;
	@Autowired
	private DetFactServiceImpl detailFactureService;
	
	@PostMapping("/par-categorie")
	@ResponseBody
	public float chiffreAffaireCategorie(@RequestBody ChiffreAffaireParCategorieClientDTO dto)
	{
		float ca = factureService.getChiffreAffaireParCategorieClient(dto.getCategorieClient(),dto.getStartDate(), dto.getEndDate());
		return ca;
	}
	
	@PostMapping("/par-produit")
	@ResponseBody
	public float revenuGenereProduit(@RequestBody RevenuGenereProduitDTO dto)
	{
		float revenuGenere = detailFactureService.getRevenuBrutProduit(dto.getIdProduit(), dto.getStartDate(), dto.getEndDate());
		return revenuGenere;
	}
	
	@PostMapping("/add/{client-id}")
	@ResponseBody
	public Facture addFacture(@PathVariable("client-id") Long clientId,@RequestBody Facture f)
	{
		Facture facture = factureService.addFacture(f, clientId);
		return facture;
		
		/*
		 * TEST sur Postman
    	{
	        "active": true,
	        "detailsFacture": [
	            {
	                "qte" : 23,
	                "pourcentageRemise" : 23,
	                "produit" : {
	                    "idProduit" : 1,
	                    "prixUnitaire" : 23.02,
	                    "code" : "828XLSK829",
	                    "libelle" : "Magrouna"
	                }
	            },
	            {
	                "qte" : 10,
	                "pourcentageRemise" : 50,
	                "produit" : {
	                    "idProduit" : 2,
	                    "prixUnitaire" : 7.02,
	                    "code" : "828XLSK829",
	                    "libelle" : "Mefte7 7"
	                }
	            }
        	]
    	}
		 */
	}
	
	@PostMapping("/progression")
	@ResponseBody
	public float checkProgressionEntreprise(@RequestBody CheckProgressDTO f)
	{
		float progress = factureService.getProgressionEntreprise(f.getStartDate(), f.getEndDate());
		return progress;
	}
}
