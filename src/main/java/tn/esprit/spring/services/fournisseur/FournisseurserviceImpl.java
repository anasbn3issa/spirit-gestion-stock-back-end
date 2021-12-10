package tn.esprit.spring.services.fournisseur;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Fournisseur;
import tn.esprit.spring.repositories.FournisseurRepository;

@Service
public class FournisseurserviceImpl implements FournisseurService {

	@Autowired
	FournisseurRepository fournisseurRepo;

	@Transactional
	public Fournisseur addFournisseur(Fournisseur fournisseur) {
		// TODO Auto-generated method stub
		return this.fournisseurRepo.save(fournisseur);
	}

	@Override
	public Fournisseur retriveFournisseur(Long id) {
		// TODO Auto-generated method stub
		return this.fournisseurRepo.findById(id).orElse(null);
	}

}
