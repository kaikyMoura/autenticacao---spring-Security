package com.lab.ecommercebackend.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataInjection {

	@Bean
	CommandLineRunner initializarDataBase(JdbcTemplate jdbcTemplate) {
		return args -> {
            jdbcTemplate.update("INSERT INTO tbroles (id, name) VALUES (?, ?)", 1, "ROLE_CUSTOMER");
            jdbcTemplate.update("INSERT INTO tbroles (id, name) VALUES (?, ?)", 2, "ROLE_ADMINISTRATOR");
            jdbcTemplate.update("INSERT INTO tbroles (id, name) VALUES (?, ?)", 3, "ROLE_SELLER");
        };
	}
}
