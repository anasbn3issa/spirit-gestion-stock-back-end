package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Produit;
import tn.esprit.spring.repositories.ClientRepository;
import tn.esprit.spring.services.client.ClientServiceImpl;

@Slf4j
@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "Client managment")
public class ClientRestController {

	@Autowired
	ClientServiceImpl clientService;

// http://localhost:8089/SpringMVC/client/retrieve-all-clients
	@GetMapping("/retrieve-all-clients")
	@ResponseBody
	public List<Client> getClients() {
		List<Client> listClients = clientService.retrieveAllClients();
		return listClients;
	}

//http://localhost:8089/SpringMVC/client/retrieve-client/8
	@GetMapping("/retrieve-client/{client-id}")
	@ResponseBody
	public Client retrieveClient(@PathVariable("client-id") Long clientId) {
		return clientService.retrieveClient(clientId);
	}

//http://localhost:8089/SpringMVC/client/add-client
	@PostMapping("/add-client")
	@ResponseBody
	public Client addClient(@RequestBody Client c) {
		Client client = clientService.addClient(c);
		return client;
	}

	// http://localhost:8089/SpringMVC/client/remove-client/{client-id}
	@DeleteMapping("/remove-client/{client-id}")
	@ResponseBody
	public void removeClient(@PathVariable("client-id") Long clientId) {
		clientService.deleteClient(clientId);
	}

	// http://localhost:8089/SpringMVC/client/modify-client
	@PutMapping("/modify-client")
	@ResponseBody
	public Client modifyClient(@RequestBody Client client) {
		return clientService.updateClient(client);
	}

	// http://localhost:8089/SpringMVC/client/retrieve-client/8
	@GetMapping("/income-from-client/{client-id}")
	@ResponseBody
	public float incomeFromClient(@PathVariable("client-id") Long clientId) {
		return clientService.incomeFromClient(clientId);
	}

	// http://localhost:8089/SpringMVC/client/purchase-history-client/1
	@GetMapping("/purchase-history-client/{client-id}")
	@ResponseBody
	public List<Produit> purchaseHistoryClient(@PathVariable("client-id") Long clientId) {
		return clientService.purchaseHistory(clientId);
	}
	
	@PutMapping("/update-incomes-from-clients")
	@ResponseBody
	public void updateIncomesFromClients() {
		log.info("update-incomes-from-clients in service");
		clientService.updateIncomesFromClients();
		
	}
	
	@PutMapping("/update-categorie-clients")
	@ResponseBody
	public void updateCategorieClients() {
		log.info("update-categorie-clients");
		clientService.updateCategorieClient();

	}
	

}