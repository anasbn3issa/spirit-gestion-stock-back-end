package tn.esprit.spring.services.livraison;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.EtatFacture;
import tn.esprit.spring.entities.EtatLivraison;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.Livraison;
import tn.esprit.spring.entities.Livreur;
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

	@Transactional
	@Override
	public Livraison addLivraison(Long factureId, Long livreurId) {
		// TODO Auto-generated method stub
		Livraison l = new Livraison();
		l.setCode("LV-"+livraisonRepository.count());
		l.setEtat(EtatLivraison.Nouveau);
		Facture f = factureRepository.findById(factureId).orElse(null);
		f.setEtat(EtatFacture.Traitement);
		
		l.setFacture(factureRepository.save(f));
		l.setLivreur(livreurRepository.findById(livreurId).orElse(null));
		
		LocalDateTime ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
		Date to = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		Date from = Date.from(ldt.plusDays(2).atZone(ZoneId.systemDefault()).toInstant());
		l.setDateLivraison(to);
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

	@Override
	public List<Object[]> getLivraisonsCountbyLivId(Long id) {
		// TODO Auto-generated method stub
		return livraisonRepository.getLivraisonsCountbyLivId(id);
	}

	@Override
	public HashMap<String, Object> livraisonspagination(Integer pageNo, Integer pageSize, String filter) {
		
		// TODO Auto-generated method stub
				PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by("etat").and(Sort.by("dateLivraison").descending()));
		        //Page<Livreur> pagedResult = livreurRepository.findAll(paging);
				//PageRequest paging = PageRequest.of(pageNo, pageSize);
				Page<Livraison> pagedResult = null;
				if(filter!=null && !filter.equals("")) {
					log.info("not null");
					pagedResult = livraisonRepository.filterLivraisonList(filter, paging); 
				}else {
					pagedResult = livraisonRepository.findAll(paging); 
					log.info("null");
				}
				HashMap<String, Object> map = new HashMap<>();
		        if(pagedResult.hasContent()) {
		    		map.put("livraisons", pagedResult.getContent());
		    		map.put("total", livraisonRepository.retrieveLivraisonCount());
		        } else {
		        	map.put("livraisons", pagedResult.getContent());
		    		map.put("total", 0);
		    	}
		        return map;
	}
}
