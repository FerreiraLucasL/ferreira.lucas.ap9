package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		//
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) -> {
			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);
			//carga de datos
			Client client1 = new Client("jorge","delsoto","jorgelin@gmail.com");
			Client client2 = new Client("pedro","alcazar","aksdoakmsd@gmail.com");
			Account account1 = new Account("VIN001", 5000.0, today);
			Account account2 = new Account("VIN002", 7000.0, tomorrow);
			Account account3 = new Account("VIN003", 10000.0, today);
			Transaction transaction1 = new Transaction(TransactionType.DEBIT, 10000.0, "lorem ipsum :v ", today);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 5000.0, "lorem ipsum :v ", today);
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 3000.0, "lorem ipsum :v ", today);
			//asignaciones
			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client1.addTransaction(transaction1,account1);
			client1.addTransaction(transaction2,account2);
			client2.addTransaction(transaction3,account3);

			//persistencia
			clientRepository.save(client1);
			clientRepository.save(client2);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);

		};
	}
}
