package com.daniloewerton.todolist.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI().info(
                new Info()
                        .title("To-do List")
                        .description("Organize your taks by using this application")
                        .contact(new Contact()
                                .name("Danilo Ewerton")
                                .email("danilo.ewe@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                .description("Github")
                .url("https://github.com/daniloewerton"));
    }
}