package com.punittewani.blogapis.blogapis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
        .info(new Info().title("Swagger API").version("1.0.0").description("Blog APIS Developed by Punit Tewani").version("0.0.1").contact(new Contact().name("Punit Tewani").url("https://github.com/punit0405").email("punit.tewani.ext@intive.com"))
        .license(new License().name("MIT"))
        );
    }
}
