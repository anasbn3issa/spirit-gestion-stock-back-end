package tn.esprit.spring.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import tn.esprit.spring.entities.Livraison;
import tn.esprit.spring.services.livraison.LvrServiceImpl;

@RestController
@Api(tags = "Livraison management")
@RequestMapping("/livraison")
public class LivraisonRestController {
	@Autowired
	LvrServiceImpl livraisonService;
	
	@ApiOperation(value = "Récupérer la liste des livraison")
	@GetMapping("/retrieve-livraisons")
	@ResponseBody
	public ResponseEntity<HashMap<String,Object>> getlivraisons(
			 @RequestParam(defaultValue = "0") Integer pageNo, 
             @RequestParam(defaultValue = "8") Integer pageSize,
             @RequestParam(required = false) String filter
	) {
	    return new ResponseEntity<>(livraisonService.livraisonspagination(pageNo, pageSize, filter), HttpStatus.OK);
		//return ResponseEntity.ok().;
	}
	
	@ApiOperation(value = "Compter les livraisons par livreur")
	@GetMapping("/count/{livreur-id}")
	@ResponseBody
	public ResponseEntity<List<Object[]>> getlivraisonsCountById(
			@PathVariable("livreur-id") Long livreurid
	) {
	    return new ResponseEntity<>(livraisonService.getLivraisonsCountbyLivId(livreurid), HttpStatus.OK);
		//return ResponseEntity.ok().;
	}
	
	@ApiOperation(value = "Récupérer la liste des livraisons")
	@GetMapping("/retrieve-all-livraisons")
	@ResponseBody
	public List<Livraison> getlivraisons() {
		return livraisonService.retrieveAllLivraisons();
	}
	
	@ApiOperation(value = "Récupérer une livraison par Id")
	@GetMapping("/retrieve-livraison/{livraison-id}")
	@ResponseBody
	public Livraison retrieveLivraison(@PathVariable("livraison-id") Long livraisonid) {
	return livraisonService.retrieveLivraison(livraisonid);
	}
	
	@ApiOperation(value = "Ajouter une nouvelle livraison")
	@PostMapping("/add-livraison/{facture-id}/livreur/{livreur-id}")
	@ResponseBody
	public Livraison addLivraison(@PathVariable("facture-id") Long factureId, @PathVariable("livreur-id") Long livreurId)
	{
		return livraisonService.addLivraison(factureId, livreurId);
	}
	
	@ApiOperation(value = "Supprimer une livraison par Id")
	@DeleteMapping("/remove-livraison/{livraison-id}")
	@ResponseBody
	public ResponseEntity<HashMap<String,Object>> removeLivraison(@PathVariable("livraison-id") Long livraisonId) {
		
		 return new ResponseEntity<>(livraisonService.deleteLivraison(livraisonId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Mettre a jour une livraison")
	@PutMapping("/modify-livraison")
	@ResponseBody
	public Livraison modifyLivraison(@RequestBody Livraison livraison) {
	return livraisonService.updateLivraison(livraison);
	}

}
