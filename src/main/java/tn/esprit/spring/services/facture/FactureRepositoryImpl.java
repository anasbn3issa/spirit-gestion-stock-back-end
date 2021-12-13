package tn.esprit.spring.services.facture;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.CategorieClient;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.DetailFacture;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.repositories.DetailFactRepository;
import tn.esprit.spring.repositories.FactureRepository;
import tn.esprit.spring.services.client.CltServiceImpl;
import tn.esprit.spring.services.detailFact.DtFctServiceImpl;
import tn.esprit.spring.services.produit.PrdServiceImpl;
import tn.esprit.spring.services.client.ClientServiceImpl;
import tn.esprit.spring.services.detailFact.DetFactServiceImpl;

@Slf4j
@Service
public class FactureRepositoryImpl implements FactureRepositoryCustom{
	
	@Autowired
	private FactureRepository factureRepository;

	@Autowired
	private CltServiceImpl clientService;
	
	@Autowired
	private DtFctServiceImpl detailFacService;
	
	@Autowired
	private PrdServiceImpl prdServiceImpl;
	
	@Autowired
	private DetailFactRepository detailFactRepository;

	@Autowired
	private DetFactServiceImpl detailFactureService;

	@Override
	public List<Facture> retrieveAllFacture() {
		return (List<Facture>) factureRepository.findAll();
	}

	@Transactional
	@Override
	public Facture addFacture(Facture f, Long idClient) {
		
		float montantRemise = 0;
		float montantSansRemise = 0;
		
		Client client = clientService.retrieveClient(idClient);
		
		for(DetailFacture df : f.getFactDetails()) {
			Produit p = df.getProduit();

			df.setMontantRemise(((p.getPrixUnitaire()*df.getPourcentageRemise())/100)*df.getQte());
			df.setPrixTotal(p.getPrixUnitaire()*df.getQte());
			
			montantRemise = montantRemise + df.getMontantRemise();
			montantSansRemise = montantSansRemise + df.getPrixTotal();
		}

		f.setDateFacture(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		f.setMontantRemise(montantRemise);
		f.setMontantFacture(montantSansRemise - montantRemise);
		f.setClient(client);
		
		factureRepository.save(f);
		
		for(DetailFacture df : f.getFactDetails()) {
			df.setFacture(f);
			detailFactureService.addFctDetail(df);
		}

		return f;
	}

	@Override
	public void deleteFacture(Long id) {
		factureRepository.deleteById(id);
	}

	@Override
	public Facture updateFacture(Facture f) {
		return factureRepository.save(f);
	}

	@Override
	public Facture retrieveFacture(Long id) {
		return factureRepository.findById(id).orElse(null);
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
		return factureRepository.retrieveFacturesByDates(date1, date2);
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
		return factureRepository.getFacturesByClient(idClient);
	}

/*
	public float getChiffreAffaireParCategorieClient(CategorieClient categorieClient, Date startDate, Date endDate) {
		
		List<Facture> factures= factureRepository.retrieveFacturesDansIntervalleEtCategorieClient(startDate, endDate, categorieClient);
		float chiffreAffaire = 0;
		for(Facture f : factures)
			chiffreAffaire = chiffreAffaire + f.getMontantFacture();
		
		return chiffreAffaire;
	}
*/

	@Override
	public float getChiffreAffaireParCategorieClient(CategorieClient categorieClient, Date startDate, Date endDate) {
		return factureRepository.getChiffreAffaireParCategorieClient(categorieClient, startDate, endDate);
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

	@Override
	public List<Facture> getOpenFactures() {
		// TODO Auto-generated method stub
		return factureRepository.getActiveFactures();
	}
	
	public float getProgressionEntreprise(Date startDate, Date endDate) {
		List<Facture> factures= factureRepository.retrieveFacturesDansIntervalle(startDate, endDate);
		
		float progression = 0;
		
		for(Facture f : factures) {
			progression = progression + f.getMontantFacture();
		}
		
		return progression;
	}

	@Override	
	@Scheduled(cron = "0 0 0 1 * *" )
	public void getProgressionEntrepriseCreationToPresent() {
		Facture oldestFacture = factureRepository.findFirstByOrderByDateFactureAsc();
		float prog = getProgressionEntreprise(oldestFacture.getDateFacture(), Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		System.out.println("Progression actuelle : " + prog);
		
	}
}
