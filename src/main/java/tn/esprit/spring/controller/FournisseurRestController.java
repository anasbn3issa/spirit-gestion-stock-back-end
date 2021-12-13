package tn.esprit.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.spring.entities.Fournisseur;
import tn.esprit.spring.services.fournisseur.FournisseurService;

@RestController
@Api(tags ="provider managment")
@RequestMapping("/provider")
public class FournisseurRestController {

	@Autowired
	FournisseurService fournisseurService;
	
	@ApiOperation("add provider")
	@PostMapping("/add-provider")
	@ResponseBody
	public Fournisseur addProvider(@RequestBody Fournisseur f) {
		
		return this.fournisseurService.addFournisseur(f);
	}
	@ApiOperation("get provider by id")
	@GetMapping("retrive-provider/{provider-id}")
	@ResponseBody
	public Fournisseur getProviderById(@PathVariable("provider-id") Long providerId ) {
		
		return fournisseurService.retriveFournisseur(providerId);
	}
}
