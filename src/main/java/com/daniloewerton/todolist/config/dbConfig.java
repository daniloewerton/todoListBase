package com.daniloewerton.todolist.config;

import com.daniloewerton.todolist.services.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class dbConfig {

    private final DatabaseService databaseService;

    @Bean
    public void database() {
        databaseService.insertDB();
    }
}
