package tn.esprit.spring.services.facture;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.CategorieClient;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.DetailFacture;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.repositories.DetailFactRepository;
import tn.esprit.spring.repositories.FactureRepository;
import tn.esprit.spring.services.client.CltServiceImpl;
import tn.esprit.spring.services.detailFact.DtFctServiceImpl;
import tn.esprit.spring.services.produit.PrdServiceImpl;

@Slf4j
@Service
public class FactureRepositoryImpl implements FactureRepositoryCustom{
	
	@Autowired
	private FactureRepository facturerepository;
	
	@Autowired
	private CltServiceImpl clientService;
	
	@Autowired
	private DtFctServiceImpl detailFacService;
	
	@Autowired
	private PrdServiceImpl prdServiceImpl;
	
	@Autowired
	private DetailFactRepository detailFactRepository;

	@Override
	public List<Facture> retrieveAllFacture() {
		return (List<Facture>) facturerepository.findAll();
	}

	@Transactional
	public Facture addFacture(Facture f, Long idClient) {
		Client client = clientService.retrieveClient(idClient);
		f.setClient(client);
		
		float montantTotalFacture=0f;
		float montantRemiseFacture=0f;
		for (DetailFacture detailFact : f.getFactDetails()) {
			float montantDetail=0f;
			float montantRemiseDetail=0f;
			montantDetail=detailFact.getProduit().getPrixUnitaire()*detailFact.getQte();
			montantRemiseDetail = montantDetail*detailFact.getPourcentageRemise()/100;
			detailFact.setPrixTotal(montantDetail);
			detailFact.setMontantRemise(montantRemiseDetail);
			detailFacService.updateFctDetail(detailFact);
			montantTotalFacture+=montantDetail;
			montantRemiseFacture+=montantRemiseDetail;
		}
		
		f.setMontantFacture(montantRemiseFacture);
		f.setMontantRemise(montantRemiseFacture);
		
		Date date = new Date(System.currentTimeMillis());
		f.setDateFacture(date);
		
		facturerepository.save(f);
		for (DetailFacture detailFact : f.getFactDetails()) {
			detailFact.setFacture(f);
			detailFacService.updateFctDetail(detailFact);
			
		}
		return f;
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

	@Override
	public List<Facture> retrieveFacturesByDates(Date date1, Date date2) {
		return facturerepository.retrieveFacturesByDates(date1, date2);
	}
	
	@Scheduled(cron = "0 0 0 1 * *" )
	public void calculeCA() {
		LocalDateTime ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
		Date to = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		Date from = Date.from(ldt.minusYears(1).atZone(ZoneId.systemDefault()).toInstant());
		List<Facture> facts = retrieveFacturesByDates(to, from);
		float sum =0f;
		try {
			for (Facture facture : facts) {
				sum+= facture.getMontantFacture()-facture.getMontantRemise();
			}
			log.info("entre "+ldt.toString()+" et "+ldt.minusYears(1)+"....");
			log.info("entre "+facts.size()+" facture:");
			log.info("CA = "+sum+" TND");	
		}catch(NullPointerException e) {
			log.info("Aucune facture trouvée...");
		}
			
	}

	@Override
	public List<Facture> getFacturesByClient(Long idClient) {
		return facturerepository.getFacturesByClient(idClient);
	}

	@Override
	public float getChiffreAffaireParCategorieClient(CategorieClient categorieClient, Date startDate, Date endDate) {
		return facturerepository.getChiffreAffaireParCategorieClient(categorieClient, startDate, endDate);
	}

	@Override
	public float getRevenuBrutProduit(Long idProduit, Date startDate, Date endDate) {
		float res=0f;

		List<Facture> facts = this.retrieveFacturesByDates(startDate, endDate);
		Set<DetailFacture> detailFactures = null;
		for (Facture facture : facts) {
			detailFactures.addAll(detailFactRepository.getDetFactByFactureProduit(facture.getIdFacture(), idProduit));
		}
		Produit produit = prdServiceImpl.retrieveProduit(idProduit);
		for (DetailFacture detailFacture : detailFactures) {
			res+= detailFacture.getQte()*produit.getPrixUnitaire();
		}
		return res;
		
	}
}
