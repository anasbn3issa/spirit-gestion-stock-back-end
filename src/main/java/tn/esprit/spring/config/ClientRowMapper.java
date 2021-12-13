package tn.esprit.spring.config;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.services.client.ClientServiceImpl;

@Slf4j
public class ClientRowMapper implements RowMapper<Client> {
	@Autowired
	ClientServiceImpl serviceClient;
	
	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		Client client = new Client();
		//client = serviceClient.retrieveClient(rs.getLong("id_client"));
		client.setNom(rs.getString("nom"));
		client.setPrenom(rs.getString("prenom"));
		client.setIdClient(rs.getLong("id_client"));
		client.setIncomeInTheLast24h(rs.getFloat("income_in_the_last24h"));
		client.setEmail(rs.getString("email"));
		client.setDateNaissance(rs.getDate("date_naissance"));
		client.setPhoto(rs.getString("photo"));
		client.setProfession(rs.getString("profession"));
		
		log.info("my client in CLientRowMapper:"+client);
		return client;
	}

}
