package tn.esprit.spring.services.livraison;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.EtatLivraison;
import tn.esprit.spring.entities.Livraison;
import tn.esprit.spring.repositories.ClientRepository;
import tn.esprit.spring.repositories.FactureRepository;
import tn.esprit.spring.repositories.LivraisonRepository;
import tn.esprit.spring.repositories.LivreurRepository;
import tn.esprit.spring.services.client.CltServiceImpl;

@Slf4j
@Service
public class LvrServiceImpl implements LivraisonServiceImpl {
	
	@Autowired
	private LivraisonRepository livraisonRepository;
	
	@Autowired
	private FactureRepository factureRepository;
	
	@Autowired
	private LivreurRepository livreurRepository;
	
	@Override
	public List<Livraison> retrieveAllLivraisons() {
		// TODO Auto-generated method stub
		return (List<Livraison>) livraisonRepository.findAll();
	}

	@Override
	public Livraison addLivraison(Livraison l, Long factureId, Long livreurId) {
		// TODO Auto-generated method stub
		l.setEtat(EtatLivraison.Nouveau);
		l.setFacture(factureRepository.findById(factureId).orElse(null));
		l.setLivreur(livreurRepository.findById(livreurId).orElse(null));
		
		LocalDateTime ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
		Date to = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		Date from = Date.from(ldt.plusDays(2).atZone(ZoneId.systemDefault()).toInstant());
		l.setDateLivraisonPrevue(from);
		return livraisonRepository.save(l);
	}

	@Override
	public void deleteLivraison(Long id) {
		// TODO Auto-generated method stub
		livraisonRepository.deleteById(id);
	}

	@Override
	public Livraison updateLivraison(Livraison l) {
		// TODO Auto-generated method stub
		return livraisonRepository.save(l);
	}

	@Override
	public Livraison retrieveLivraison(Long id) {
		// TODO Auto-generated method stub
		return livraisonRepository.findById(id).orElse(null);
	}
	
}
