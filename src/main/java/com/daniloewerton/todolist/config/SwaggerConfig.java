package com.daniloewerton.todolist.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${spring.swagger.title}")
    private String title;
    @Value("${spring.swagger.description}")
    private String description;
    @Value("${spring.swagger.contact}")
    private String contact;
    @Value("${spring.swagger.email}")
    private String email;
    @Value("${spring.swagger.github}")
    private String github;
    @Value("${spring.swagger.github-url}")
    private String url;

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI().info(new Info()
                        .title(title)
                        .description(description)
                        .contact(new Contact()
                                .name(contact)
                                .email(email)))
                .externalDocs(new ExternalDocumentation()
                .description(github)
                .url(url));
    }
}