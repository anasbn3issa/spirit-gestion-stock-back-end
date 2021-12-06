package tn.esprit.spring.config;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import tn.esprit.spring.entities.Client;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
public class JDBCPaginationClientJobConfig {

	@Bean
    public ItemReader<Client> itemReader(DataSource dataSource,
                                             PagingQueryProvider queryProvider) {
        return new JdbcPagingItemReaderBuilder<Client>()
                .name("pagingItemReader")
                .dataSource(dataSource)
                .pageSize(2)
                .queryProvider(queryProvider)
                .rowMapper(new BeanPropertyRowMapper<>(Client.class))
                .build();
    }
	@Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(DataSource dataSource) {
        SqlPagingQueryProviderFactoryBean provider = 
                new SqlPagingQueryProviderFactoryBean();
 
        provider.setDataSource(dataSource);
        provider.setSelectClause("SELECT nom, prenom, email, photo, IncomeInTheLast24h, dateNaissance, profession, categorieClient");
        provider.setFromClause("FROM clients");
 
        return provider;
    }
 
}

