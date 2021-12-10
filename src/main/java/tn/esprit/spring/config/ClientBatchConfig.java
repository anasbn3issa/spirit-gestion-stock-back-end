/*package tn.esprit.spring.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.annotation.EnableScheduling;

import tn.esprit.spring.batch.ClientProcessor;
import tn.esprit.spring.batch.ClientWriter;
import tn.esprit.spring.entities.CategorieClient;
import tn.esprit.spring.entities.Client;
import tn.esprit.spring.entities.Profession;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class ClientBatchConfig {

	private static final String JOB_NAME = "listClientJob";
	private static final String STEP_NAME = "processingStep";
	private static final String READER_NAME = "clientItemReader";
	private static final String delimiter = "";


	private static final String QUERY_FIND_CLIENTS =
            "SELECT " +
                    "nom, " +
                    "prenom, " +
            "FROM Client";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;


	@Bean
	public Job listClientsJob(Step step1) {
		return jobBuilderFactory.get(JOB_NAME).start(step1).build();
	}

	@Bean
	public Step stockStep(DataSource dataSource) {
		return stepBuilderFactory.get(STEP_NAME).<Client, Client>chunk(5).reader(clientItemReader(dataSource))
				.processor(clientItemProcessor()).writer(clientItemWriter()).build();
	}
	
	@Bean
	public ItemReader<Client> clientItemReader(DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Client>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(QUERY_FIND_CLIENTS)
                .rowMapper(new BeanPropertyRowMapper<>(Client.class))
                .build();
		

	}

	@Bean
	public ItemProcessor<Client, Client> clientItemProcessor() {
		return new ClientProcessor();
	}

	@Bean
	public ItemWriter<Client> clientItemWriter() {
		return new ClientWriter();
	}
}
*/