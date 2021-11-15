package tn.esprit.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


public class SwaggerConfig {
	@Bean
	public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2)
	.select()
	.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	.paths(PathSelectors.any())
	.build().apiInfo(new ApiInfoBuilder()
			.title("Swagger Configuration for GestionMagasinStock")
			.description("\"Spring Boot Swagger configuration\"")
			.contact(new Contact("wajdi.Sadouki","https://github.com/geeekho", "wajdi.sadouki@esprit.tn"))
			.version("1.1.0").build());
	}
}
