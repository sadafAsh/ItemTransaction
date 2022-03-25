package com.soj.item.transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//first commit
@SpringBootApplication
public class ItemTransactionApplication {
static final Logger logger=LogManager.getLogger(ItemTransactionApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(ItemTransactionApplication.class, args);


	}

}
