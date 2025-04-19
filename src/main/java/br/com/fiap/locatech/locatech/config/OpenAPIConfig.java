package br.com.fiap.locatech.locatech.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI locatech() {
        return new OpenAPI()
                .info(
                        new Info().title("Locatech API")
                                .description("Projeto desenvolvido durante as aulas do curso de " +
                                        "Spring MVC - APIs RESTful na pós graduação de Arquitetura e " +
                                        "Desenvolvimento Java | FIAP")
                                .version("v.0.0.1")
                                .license(new License().name("Github - Caike Queiroz").url("https://github.com/Caike-Queiroz"))
                );
    }
}