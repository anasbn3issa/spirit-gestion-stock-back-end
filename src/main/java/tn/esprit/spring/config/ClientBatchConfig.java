package tn.esprit.spring.config;

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

	private String names = "nom,prenom,email,password,photo,IncomeInTheLast24h,dateNaissance,profession,categorieClient";

	private static final String QUERY_FIND_CLIENTS =
            "SELECT " +
                    "nom, " +
                    "prenom, " +
                    "email," +
                    "password," +
                    "photo," +
                    "IncomeInTheLast24h," +
                    "dateNaissance," +
                    "profession," +
                    "categorieClient," +
            "FROM client ";
	//private String delimiter = ",";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;


	/*7 Créer le bean associé au job et le lancer*/
	@Bean
	public Job listClientsJob(Step step1) {
		return jobBuilderFactory.get(JOB_NAME).start(step1).build();
	}

	/*8 Créer le step associé au job et le lancer*/
	@Bean
	public Step stockStep(DataSource dataSource) {
		return stepBuilderFactory.get(STEP_NAME).<Client, Client>chunk(5).reader(clientItemReader(dataSource))
				.processor(clientItemProcessor()).writer(clientItemWriter()).build();
	}
	
	/*9. étape 1 (ItemReader) Créer le reader pour récupérer les données depuis
	 * le fichier csv*/
	@Bean
	public ItemReader<Client> clientItemReader(DataSource dataSource) {
		/*FlatFileItemReader<Client> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource(FILE_NAME));
		reader.setName(READER_NAME);
		reader.setLinesToSkip(1);
		/*7. récupérer les données ligne par ligne du fichier excel */
		/*reader.setLineMapper(clientLineMapper());
		return reader;*/
		return new JdbcCursorItemReaderBuilder<Client>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(QUERY_FIND_CLIENTS)
                .rowMapper(new BeanPropertyRowMapper<>(Client.class))
                .build();
		

	}

	
	/*10. récupérer les données ligne par ligne du fichier excel */
/*
	@Bean
	public LineMapper<Client> clientLineMapper() {

		final DefaultLineMapper<Client> defaultLineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(delimiter);
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(names.split(delimiter));
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSet -> {
			Client data = new Client();
			data.setNom(fieldSet.readString(0));
			data.setPrenom(fieldSet.readString(1));
			data.setEmail(fieldSet.readString(2));
			data.setPassword(fieldSet.readString(3));
			data.setPhoto(fieldSet.readString(4));
			data.setIncomeInTheLast24h(fieldSet.readFloat(5));

			try {
				data.setDateNaissance(new SimpleDateFormat("dd/MM/yyyy").parse(fieldSet.readString(6)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data.setProfession(Profession.valueOf(fieldSet.readString(7)));
			data.setCategorieClient(CategorieClient.valueOf(fieldSet.readString(8)));
			return data;
		});
		return defaultLineMapper;
	}
*/
	/* 11. étape 2 (ItemProcessor) fait appel à la classe StockProcessor
	 * qui se charge de modifier la logique des données selon
	 * nos besoins métiers */
	@Bean
	public ItemProcessor<Client, Client> clientItemProcessor() {
		return new ClientProcessor();
	}

	
	/* 12. étape 3 (ItemWriter) fait appel à la classe StockWriter
	 * qui se charge de lancer le service de sauvegarde des 
	 * données liées à la partie stock dans la BD*/
	@Bean
	public ItemWriter<Client> clientItemWriter() {
		return new ClientWriter();
	}
}
