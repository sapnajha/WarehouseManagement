package com.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@ComponentScan(basePackages = {"com.warehouse","com.warehouse.controller","com.warehouse.repository"})
@EntityScan(basePackages = {"com.warehouse.entities"})
@EnableJpaRepositories(basePackages = {"com.warehouse.services","com.warehouse.repository"})
@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
		
	}

}
