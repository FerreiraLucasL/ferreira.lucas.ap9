package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.util.*;

import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
		return (args) -> {
			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);
			LocalDate thruDate = today.plusYears(5);
			Set<Integer> cuotasHipotecario = new HashSet<>() {};
			cuotasHipotecario.addAll(Arrays.asList(new Integer[] {12,24,36,48,60}));
			Set<Integer> cuotasPersonal = new HashSet<>() {};
			cuotasPersonal.addAll(Arrays.asList(new Integer[] {6,12,24}));
			Set<Integer> cuotasAutomotriz = new HashSet<>() {};
			cuotasAutomotriz.addAll(Arrays.asList(new Integer[] {6,12,24,36}));


			//carga de datos
			Client client1 = new Client("Melba","Morel","melba@mindhub.com", passwordEncoder.encode("Melba76."));
			Client client2 = new Client("Pedro","Alcazar","PedAlc@gmail.com",passwordEncoder.encode("PedA1"));
			Client clientA = new Client("Jorge","DelSoto","admin@mindhub.com",passwordEncoder.encode("admin"));
			Account account1 = new Account("VIN001", 5000.0, today);
			Account account2 = new Account("VIN002", 7000.0, tomorrow);
			Account account3 = new Account("VIN003", 10000.0, today);
			Transaction transaction1 = new Transaction(TransactionType.DEBIT, 10000.0, "lorem ipsum :v ", today);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT, 5000.0, "lorem ipsum :v ", today);
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 3000.0, "lorem ipsum :v ", today);
			Loan hipotecario = new Loan("Hipotecario",500000.0,cuotasHipotecario);
			Loan personal = new Loan("Personal",100000.0,cuotasPersonal);
			Loan automotriz = new Loan("Automotriz",300000.0,cuotasAutomotriz);
			Card goldMelba = new Card(CardType.DEBIT,CardColor.GOLD,"9874-8495-8121-3215",987,"MELBAMOREL",today,thruDate,client1);
			Card titaniumMelba = new Card(CardType.CREDIT,CardColor.TITANIUM,"9832-8412-6121-3815",877,"MELBAMOREL",today,thruDate,client1);
			Card silverPedro = new Card(CardType.CREDIT,CardColor.SILVER,"9832-8412-6121-3816",171,"ALCARAZPEDRO",today,thruDate,client2);

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
			loanRepository.save(hipotecario);
			loanRepository.save(automotriz);
			loanRepository.save(personal);
			//Tarjetas
			cardRepository.save(goldMelba);
			cardRepository.save(titaniumMelba);
			cardRepository.save(silverPedro);

			//solicitudes de prestamo
			ClientLoan melbaHipotecario = new ClientLoan( 60,400000.0);
			client1.addClientLoan(melbaHipotecario);
			hipotecario.addClientLoan(melbaHipotecario);
			clientLoanRepository.save(melbaHipotecario);

			ClientLoan melbaPersonal = new ClientLoan(12,50000.0);
			client1.addClientLoan(melbaPersonal);
			personal.addClientLoan(melbaPersonal);
			clientLoanRepository.save(melbaPersonal);

			ClientLoan pedroPersonal = new ClientLoan(24,100000.0);
			client2.addClientLoan(pedroPersonal);
			personal.addClientLoan(pedroPersonal);
			clientLoanRepository.save(pedroPersonal);

			ClientLoan pedroAutomotriz = new ClientLoan(36,200000.0);
			client2.addClientLoan(pedroAutomotriz);
			automotriz.addClientLoan(pedroAutomotriz);
			clientLoanRepository.save(pedroAutomotriz);

		};
	}
}
