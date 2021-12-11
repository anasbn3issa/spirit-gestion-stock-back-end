package tn.esprit.spring.services.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
//import tn.esprit.spring.BatchLauncher;
import tn.esprit.spring.DatabaseinputApplication;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.DetailFacture;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.repositories.ClientRepository;
import tn.esprit.spring.entities.CategorieClient;

@Slf4j
@Service
public class CltServiceImpl implements ClientServiceImpl {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	// private BatchLauncher batchLauncher;
	private DatabaseinputApplication batchLauncher2;

	@Override
	public List<Client> retrieveAllClients() {
		// TODO Auto-generated method stub
		log.info("Retreieving all clients...");
		return (List<Client>) clientRepository.findAll();
	}

	@Override
	public Client addClient(Client c) {
		// TODO Auto-generated method stub
		log.info("Adding client:\n" + c);

		return clientRepository.save(c);
	}

	@Override
	public void deleteClient(Long id) {
		log.info("Deleting client with id " + id);

		clientRepository.deleteById(id);
	}

	@Override
	public Client updateClient(Client c) {
		// TODO Auto-generated method stub
		return clientRepository.save(c);
	}

	@Override
	public Client retrieveClient(Long id) {
		// TODO Auto-generated method stub
		log.info("Updating client with id " + id);
		return clientRepository.findById(id).orElse(null);
	}

	@Override
	public List<Client> retrieveClientsByDates(Date date1, Date date2) {
		log.info("Retreieving clients dates between " + date1 + " and" + date2);
		return clientRepository.retrieveClientsByDates(date1, date2);
	}

	@Override
	public float incomeFromClient(Long idClient) {
		return clientRepository.getIncomeFromClient(idClient);
	}

	@Override
	public List<Produit> purchaseHistory(Long idClient) {
		List<DetailFacture> listDetailFacture = clientRepository.getPurchaseHistory(idClient);
		List<Produit> listProduit = new ArrayList<>();
		for (DetailFacture detf : listDetailFacture) {
			Produit p = new Produit();
			p.setCode(detf.getProduit().getCode());
			p.setLibelle(detf.getProduit().getLibelle());
			p.setIdProduit(detf.getProduit().getIdProduit());
			p.setPrixUnitaire(detf.getProduit().getPrixUnitaire());
			// listProduit.add(detf.getProduit());
			listProduit.add(p);
			log.info("-----" + p);
		}

		log.info("listProduit-------" + listProduit);
		return listProduit;
	}

	@Override
	public void updateIncomesFromClients() {
		this.updateCategorieClient();

		try {
			batchLauncher2.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// Methode qui met a jour la categorie des clients chaque 30 min en fonction du
	// nombre d'achat qu'ils ont effectué
	// En fonction du nombre d'achat, leur catégorie change et il reçoive un code
	// promo par mail pour leur prochains achats
	// @Scheduled(cron = "*/20 * * * * *")

	// @Scheduled(cron = "0 0/30 * * * *")
	@Override
	public void updateCategorieClient() {
		List<Client> Clients = (List<Client>) clientRepository.findAll();
		for (Client client : Clients) {
			// System.out.println("clients +++: " + client );

			System.out.println("verif de la categorie");
			if (client.getFactures().size() <= 3 && client.getCategorieClient() != CategorieClient.Ordinaire) {
				log.info(client.getPrenom() + " " + client.getNom() + "is becoming Ordinaire with a total of "
						+ client.getFactures().size() + "factures");
				clientRepository.updateCategorieClient(client.getIdClient(), CategorieClient.Ordinaire);

			}
			if (client.getFactures().size() >= 4 && client.getFactures().size() <= 5
					&& client.getCategorieClient() != CategorieClient.Fidele) {

				log.info(client.getPrenom() + " " + client.getNom() + "is becoming Fidele with a total of "
						+ client.getFactures().size() + "factures");
				clientRepository.updateCategorieClient(client.getIdClient(), CategorieClient.Fidele);

			}
			if (client.getFactures().size() >= 6 && client.getCategorieClient() != CategorieClient.Premium) {

				log.info(client.getPrenom() + " " + client.getNom() + "is becoming Premium with a total of "
						+ client.getFactures().size() + "factures");
				clientRepository.updateCategorieClient(client.getIdClient(), CategorieClient.Premium);
			}
		}
	}

}
