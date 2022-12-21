package com.example.accessingdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingdatajpaApplication {
	private static final Logger log = LoggerFactory.getLogger(AccessingdatajpaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessingdatajpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));
			
			log.info("Customers found with findAll");
			log.info("-------------------------------");
			 for (Customer customer : repository.findAll()) {
			        log.info(customer.toString());
			      }
			 log.info("");
			 
			 log.info("Customers found with findById(1L)");
			 Customer cust1 = repository.findById(1L);
			 log.info(cust1.toString());
			 log.info("");
			 
			 log.info("Customers found with findByLastName");
			 repository.findByLastName("O'Brian").forEach( cust -> {
				 log.info(cust.toString());
			 });
			 log.info("");
		};

	}

}
