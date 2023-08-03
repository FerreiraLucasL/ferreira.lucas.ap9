package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		//
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return (args) -> {
			Client client = new Client("jorge","delsoto","jorgelin@gmail.com");
			clientRepository.save(client);
			Client client2 = new Client("pedro","alcazar","aksdoakmsd@gmail.com");
			clientRepository.save(client2);
		};
	}
}
