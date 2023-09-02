package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long>  {

}
