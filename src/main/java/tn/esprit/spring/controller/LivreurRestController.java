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
import tn.esprit.spring.entities.Livreur;
import tn.esprit.spring.services.livreur.LvreurServiceImpl;

@RestController
@Api(tags = "Livreur management")
@RequestMapping("/livreur")
public class LivreurRestController {
	@Autowired
	LvreurServiceImpl livreurService;
	
	@ApiOperation(value = "Récupérer la liste des livreurs")
	@GetMapping("/retrieve-all-livreurs")
	@ResponseBody
	public List<Livreur> getlivraisons() {
		return livreurService.retrieveAllLivreurs();
	}
	
	@ApiOperation(value = "Récupérer un livreur par Id")
	@GetMapping("/retrieve-livreur/{livreur-id}")
	@ResponseBody
	public Livreur retrieveLivreur(@PathVariable("livreur-id") Long livreurid) {
	return livreurService.retrieveLivreur(livreurid);
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
	public Livreur modifyLivreur(@RequestBody Livreur livreur) {
	return livreurService.updateLivreur(livreur);
	}
}
