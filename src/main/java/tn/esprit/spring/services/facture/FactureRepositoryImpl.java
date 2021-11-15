package tn.esprit.spring.services.facture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.repositories.FactureRepository;

@Service
public class FactureRepositoryImpl implements FactureRepositoryCustom{
	
	@Autowired
	private FactureRepository facturerepository;

	@Override
	public List<Facture> retrieveAllFacture() {
		return (List<Facture>) facturerepository.findAll();
	}

	@Override
	public Facture addFacture(Facture f) {
		return facturerepository.save(f);
	}

	@Override
	public void deleteFacture(Long id) {
		facturerepository.deleteById(id);
	}

	@Override
	public Facture updateFacture(Facture f) {
		return facturerepository.save(f);
	}

	@Override
	public Facture retrieveFacture(Long id) {
		return facturerepository.findById(id).orElse(null);
	}

	@Override
	public void cancelFacture(Long id) {
			Facture facture = retrieveFacture(id);
			if(facture != null) {
				facture.setActive(false);
				updateFacture(facture);
			}
	}
}
