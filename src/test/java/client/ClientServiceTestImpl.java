package client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.services.client.ClientServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ClientServiceTestImpl {
	@Autowired
    private ClientServiceImpl clientService;
	
	//updateIncomesFromClients
	
	@Test 
	void testIncomesWithBatch() {
		clientService.updateIncomesFromClients();
	}
}
