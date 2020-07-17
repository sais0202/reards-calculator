package com.customer.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("classpath:rewards-threshold.properties")
public class RewardsCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardsCalculatorApplication.class, args);
	}

}
