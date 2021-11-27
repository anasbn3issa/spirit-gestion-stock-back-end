package tn.esprit.spring.services.client;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.repositories.ClientRepository;

@Slf4j
@Service
public class CltServiceImpl implements ClientServiceImpl{
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public List<Client> retrieveAllClients() {
		// TODO Auto-generated method stub
		log.info("Retreieving all clients...");
		return (List<Client>) clientRepository.findAll();
	}

	@Override
	public Client addClient(Client c) {
		// TODO Auto-generated method stub
		log.info("Adding client:\n"+c);

		return clientRepository.save(c);
	}

	@Override
	public void deleteClient(Long id) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		log.info("Deleting client with id "+id);

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
		log.info("Updating client with id "+id);
		return clientRepository.findById(id).orElse(null);
	}

	@Override
	public List<Client> retrieveClientsByDates(Date date1, Date date2) {
		log.info("Retreieving clients dates between "+date1+ " and"+ date2);
		return clientRepository.retrieveClientsByDates(date1, date2) ;
	}

}
