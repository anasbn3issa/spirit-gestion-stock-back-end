package tn.esprit.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tn.esprit.spring.services.stock.StockServiceImpl;

@EnableScheduling
@EnableAspectJAutoProxy
@EnableSwagger2
@SpringBootApplication
public class GestionMagasinStockBackendSpiritApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionMagasinStockBackendSpiritApplication.class, args);
		
	}

}
