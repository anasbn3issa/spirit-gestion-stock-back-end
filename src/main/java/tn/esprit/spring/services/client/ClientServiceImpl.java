package tn.esprit.spring.services.client;

import java.util.Date;
import java.util.List;

import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Produit;


public interface ClientServiceImpl {
	
	List<Client> retrieveAllClients();

	Client addClient(Client c);

	void deleteClient(Long id);

	Client updateClient(Client c);

	Client retrieveClient(Long id);
	
	List<Client> retrieveClientsByDates(Date date1, Date date2);
	
	float incomeFromClient(Long idClient);

	List<Produit> purchaseHistory(Long idClient);
	
	void updateIncomesFromClients();
	
	public void updateCategorieClient();

}
