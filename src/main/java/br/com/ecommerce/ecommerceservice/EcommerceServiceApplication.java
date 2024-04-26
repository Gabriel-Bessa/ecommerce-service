package br.com.ecommerce.ecommerceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@SpringBootApplication
public class EcommerceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceServiceApplication.class, args);
	}

}
