package com.lab.ecommercebackend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI myOpenAPI() {

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8089");
        localServer.setDescription("Ambiente local");

        Info info = new Info()
                .title("E-commerce API")
                .version("1.0")
                .description("Documentação do projeto e-commerce");
        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
