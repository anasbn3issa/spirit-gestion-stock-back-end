package tn.esprit.spring.services.facture;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Facture;
import tn.esprit.spring.entities.CategorieClient;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.DetailFacture;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.repositories.FactureRepository;
import tn.esprit.spring.services.client.ClientServiceImpl;
import tn.esprit.spring.services.detailFact.DetFactServiceImpl;

@Service
public class FactureRepositoryImpl implements FactureRepositoryCustom{
	
	@Autowired
	private FactureRepository factureRepository;
	@Autowired
	private ClientServiceImpl clientService;
	@Autowired
	private DetFactServiceImpl detailFactureService;

	@Override
	public List<Facture> retrieveAllFacture() {
		return (List<Facture>) factureRepository.findAll();
	}

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
			detailFactureService.addProdDetail(df);
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
	public float getChiffreAffaireParCategorieClient(CategorieClient categorieClient, Date startDate, Date endDate) {
		
		List<Facture> factures= factureRepository.retrieveFacturesDansIntervalleEtCategorieClient(startDate, endDate, categorieClient);
		float chiffreAffaire = 0;
		for(Facture f : factures)
			chiffreAffaire = chiffreAffaire + f.getMontantFacture();
		
		return chiffreAffaire;
	}

	@Override
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
