package tn.esprit.spring.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.services.client.ClientServiceImpl;

@Slf4j
public class ClientProcessor implements ItemProcessor<Client,Client > {
	@Autowired
	ClientServiceImpl clientService;

	@Override
	public Client process(Client item) throws Exception {
		//Client retrievedClient = new Client();
		/*item = clientService.retrieveClient(item.getIdClient());*/
		log.info("the client retrieved to calculate revenu :"+item);
		float incomeFromClient = clientService.incomeFromClient(item.getIdClient());
		item.setIncomeInTheLast24h(incomeFromClient);
		return item;
	}

}
