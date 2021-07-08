package com.tran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class TransactionProcessingApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TransactionProcessingApplication.class, args);
	}
}
