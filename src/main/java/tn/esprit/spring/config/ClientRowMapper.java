package tn.esprit.spring.config;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Client;

@Slf4j
public class ClientRowMapper implements RowMapper<Client> {
	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		Client client = new Client();
		client.setNom(rs.getString("nom"));
		client.setPrenom(rs.getString("prenom"));
		client.setIdClient(rs.getLong("id_client"));
		client.setIncomeInTheLast24h(rs.getFloat("income_in_the_last24h"));
		log.info("my client in CLientRowMapper:"+client);
		return client;
	}

}
