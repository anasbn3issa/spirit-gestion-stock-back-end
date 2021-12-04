package tn.esprit.spring.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.services.client.ClientServiceImpl;


@Slf4j
public class ClientWriter implements ItemWriter<Client> {

	@Autowired
	ClientServiceImpl clientService;
	@Override
	public void write(List<? extends Client> items) throws Exception {
        //List<tn.esprit.spring.entities.Client> clients= clientService.retrieveAllClients();

		log.info("Mise à jour de la valeur du revenu de client quotidienne");
		for( Client c:items) {
        	System.out.println("writing item..: " + c);
            clientService.updateClient(c);
        }
		
	}

}
