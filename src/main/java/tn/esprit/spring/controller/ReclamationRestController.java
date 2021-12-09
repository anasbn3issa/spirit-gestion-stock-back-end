package tn.esprit.spring.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.spring.config.MyConstants;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.services.fournisseur.FournisseurserviceImpl;
import tn.esprit.spring.services.produit.PrdServiceImpl;
import tn.esprit.spring.services.reclamation.RecServiceImpl;

@RestController
@Api(tags = "claim management")
@RequestMapping("/claim")
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamationRestController {
	
	@Autowired
	RecServiceImpl ReclamationService;
	@Autowired
	FournisseurserviceImpl FournisseurService;
	
	 @Autowired
	    public JavaMailSender emailSender;
	
	@ApiOperation(value = "Récupérer la liste des reclamations")
	@GetMapping("/get-all-claims")
	@ResponseBody
	@JsonManagedReference
	public List<Reclamation> getRec() {
	List<Reclamation> listReclamations = ReclamationService.retrieveAllReclamations();
	return listReclamations;
	}
	@ApiOperation(value = "Récupérer une réclamation par Id")
	@GetMapping("/retrieve-claim/{claim-id}")
	@ResponseBody
	public Reclamation retrieveRec(@PathVariable("claim-id") Long recId) {
	return ReclamationService.retrieveReclamation(recId);
	}
	
	@ApiOperation(value = "Ajouter une reclamation")
	@PostMapping("/add-claim/{idProduit}")
	@ResponseBody
	public Reclamation addRec(@RequestBody Reclamation r, @PathVariable Long idProduit)
	{	
		return ReclamationService.addReclamation(r, idProduit);
	}
	
	@ApiOperation(value = "Supprimer une reclamation par Id")
	@DeleteMapping("/remove-claim/{claim-id}")
	@ResponseBody
	public void removeRec(@PathVariable("claim-id") Long RecId) {
		ReclamationService.deleteReclamation(RecId);
	}
	
	@ApiOperation(value="get rec by product id")
	@GetMapping("/get-claim-by-produit/{idProduit}")
	@ResponseBody
	public List<Reclamation> getRecByProduitId(@PathVariable("idProduit") Long idProduit) {
		return this.ReclamationService.retrieveRecByProduitId(idProduit);

	}
	
	@GetMapping("/stat")
	@ResponseBody
	public HashMap<String, Integer> statistique () {
		  return ReclamationService.Claimmax();
	}
	
	@GetMapping("/worst")
	@ResponseBody
	public Produit WorstProduct()
	{
		return ReclamationService.WorstProduct();
		
	}


	    @ResponseBody
	    @RequestMapping("/sendSimpleEmail/{idFournisseur}")
	    public void sendSimpleEmail(@PathVariable("idFournisseur") Long idFournisseur) {

	    	Produit p = ReclamationService.WorstProduct();
	        // Create a Simple MailMessage.
	        SimpleMailMessage message = new SimpleMailMessage();
	        
	        message.setTo(FournisseurService.retriveFournisseur(idFournisseur).getEmail());
	        message.setSubject("LE STATUS DE VOTRE PRODUIT");
	        message.setText("Bonjour, Nous venons vous informer que le produit d'ID: "+p.getIdProduit()+ " a reçu le plus grand nombre de réclamations");

	        // Send Message!
	        this.emailSender.send(message);

	    }

	
}
