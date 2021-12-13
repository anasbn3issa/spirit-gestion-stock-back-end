package tn.esprit.spring.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Livreur;
import tn.esprit.spring.services.client.CltServiceImpl;
import tn.esprit.spring.services.livreur.LvreurServiceImpl;

@RestController
@CrossOrigin
@Api(tags = "Livreur management")
@RequestMapping("/livreurs")
@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LivreurRestController {
	@Autowired
	LvreurServiceImpl livreurService;
	
	@ApiOperation(value = "Récupérer la liste des livreurs")
	@GetMapping("/retrieve-all-livreurs")
	@ResponseBody
	public ResponseEntity<HashMap<String,Object>> getlivraisons(
			 @RequestParam(defaultValue = "0") Integer pageNo, 
             @RequestParam(defaultValue = "8") Integer pageSize,
             @RequestParam(required = false) String filter
	) {
	    return new ResponseEntity<>(livreurService.livreurspagination(pageNo, pageSize, filter), HttpStatus.OK);
		//return ResponseEntity.ok().;
	}
	
	@ApiOperation(value = "Récupérer la liste des livreurs actifs")
	@GetMapping("/retrieve-active-livreurs")
	@ResponseBody
	public ResponseEntity<List<Livreur>> getActivelivreur() {
	    return new ResponseEntity<>(livreurService.retrieveActiveLivreurs(), HttpStatus.OK);
		//return ResponseEntity.ok().;
	}
	
	@ApiOperation(value = "Mettre a jour la liste des livreurs")
	@PutMapping("/disable-livreurs")
	@ResponseBody
	public ResponseEntity<HashMap<String,Object>> modifyClient(@RequestBody List<Long> ids) {
		log.info("Mettre a jour la liste des livreurs...");

	    return new ResponseEntity<>(livreurService.disableLivreursWithIds(ids), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Récupérer un livreur par Id")
	@GetMapping("/retrieve-livreur/{livreur-id}")
	@ResponseBody
	public ResponseEntity<Livreur> retrieveLivreur(@PathVariable("livreur-id") Long livreurid) {
		return new ResponseEntity<>(livreurService.retrieveLivreur(livreurid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Ajouter un nouveau livreur")
	@PostMapping("/add-livreur")
	@ResponseBody
	public Livreur addLivreur(@RequestBody Livreur l)
	{
		return livreurService.addLivreur(l);
	}
	
	@ApiOperation(value = "Supprimer un livreur par Id")
	@DeleteMapping("/remove-livreur/{livreur-id}")
	@ResponseBody
	public void removeLivraison(@PathVariable("livreur-id") Long livreurId) {
		livreurService.deleteLivreur(livreurId);
	}
	
	@ApiOperation(value = "Mettre a jour un livreur")
	@PutMapping("/modify-livreur")
	@ResponseBody
	public ResponseEntity<Livreur> modifyLivreur(@RequestBody Livreur livreur) {
		return new ResponseEntity<>(livreurService.updateLivreur(livreur), HttpStatus.OK);
	}
}
