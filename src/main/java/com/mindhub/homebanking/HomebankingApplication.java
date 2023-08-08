package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		//
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args) -> {
			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);
			//carga de clientes prueba
			Client client1 = new Client("jorge","delsoto","jorgelin@gmail.com");
			clientRepository.save(client1);
			Client client2 = new Client("pedro","alcazar","aksdoakmsd@gmail.com");
			clientRepository.save(client2);
			Account account1 = new Account("VIN001", 5000.0, today);
			client1.addAccount(account1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", 7000.0, tomorrow);
			client1.addAccount(account2);
			accountRepository.save(account2);
			Account account3 = new Account("VIN003", 10000.0, today);
			client2.addAccount(account3);
			accountRepository.save(account3);
		};
	}
}
