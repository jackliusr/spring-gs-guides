package com.example.accessingdatamongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AccessingdatamongodbApplication implements CommandLineRunner {
	
    Logger log = LoggerFactory.getLogger(AccessingdatamongodbApplication.class);

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AccessingdatamongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();
		
		// save a couple of customers
		repository.save(new Customer("Alice", "Smith"));
		repository.save(new Customer("Bob", "Smith"));
		
	    // fetch all customers
		log.info("Customers found with findAll():");
		log.info("-------------------------------");
	    for (Customer customer : repository.findAll()) {
	    	log.info(customer.toString());
	    }
	    log.info("");
	    
	    log.info("Customers found with findByFirstName");
	    log.info("-------------------------------");
	    Customer cust1 = this.repository.findByFirstName("Bob");
	    log.info(cust1.toString());
	    log.info("");
	    
	    log.info("Customers found with findByLastName");
	    log.info("-------------------------------");
        this.repository.findByLastName("Smith").forEach( (cust) -> {
        	log.info(cust.toString());
        });
	    log.info("");
	    
	}

}
