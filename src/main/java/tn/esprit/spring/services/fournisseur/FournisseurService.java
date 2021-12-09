package tn.esprit.spring.services.fournisseur;

import tn.esprit.spring.entities.Fournisseur;

public interface FournisseurService {

	Fournisseur addFournisseur(Fournisseur fournisseur);

	Fournisseur retriveFournisseur(Long id);
}
