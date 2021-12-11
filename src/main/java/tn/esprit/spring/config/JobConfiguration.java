package tn.esprit.spring.config;
import java.util.HashMap;
import java.util.Map;


import javax.sql.DataSource;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.batch.ClientProcessor;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.services.client.ClientServiceImpl;
@Slf4j
@Configuration
public class JobConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ClientServiceImpl serviceClient;
	
	@Bean
	public JdbcCursorItemReader<Client> cursorItemReader(){
		JdbcCursorItemReader<Client> reader = new JdbcCursorItemReader<>();
		reader.setSql("SELECT id_client, nom, prenom, income_in_the_last24h, date_naissance, email, categorie_client, photo, profession FROM Client");
		reader.setDataSource(dataSource);
		reader.setFetchSize(100);
		reader.setRowMapper(new ClientRowMapper());
		log.info("reader in JobConfiguration JdbcCursorItemReader"+reader);
		return reader;
	}
	
	
	// This is Thread-safe
	@Bean
	public JdbcPagingItemReader<Client> pagingItemReader(){
		JdbcPagingItemReader<Client> reader = new JdbcPagingItemReader<>();
		reader.setDataSource(this.dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper(new ClientRowMapper());
		
		Map<String, Order> sortKeys = new HashMap<>();
		sortKeys.put("id_client", Order.ASCENDING);
		
		MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
		queryProvider.setSelectClause("SELECT id_client, nom, prenom, income_in_the_last24h, date_naissance, email, categorie_client, photo, profession");
		queryProvider.setFromClause("FROM Client");
		queryProvider.setSortKeys(sortKeys);


		reader.setQueryProvider(queryProvider);
		log.info("reader in JobConfiguration JdbcPagingItemReader"+reader);

		return reader;
	}
	
	@Bean
	public ItemWriter<Client> clientItemWriter(){
		return items -> {
			for(Client c : items) {
	        	float itemIncome=serviceClient.incomeFromClient(c.getIdClient());

				c.setIncomeInTheLast24h(itemIncome);
				serviceClient.updateClient(c);
				log.info("ItemWriter--------"+c);
			}
		};
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Client, Client>chunk(5)
				.reader(cursorItemReader())
				.reader(pagingItemReader())
				.writer(clientItemWriter())
				.build();
	}
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job")
				.start(step1())
				.build();
	}
}
