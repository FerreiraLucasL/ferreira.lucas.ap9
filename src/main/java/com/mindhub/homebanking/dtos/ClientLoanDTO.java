package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;
    private ClientDTO client;
    private LoanDTO loan;
    private Integer payments;
    private Double amount;

    public ClientLoanDTO(ClientLoan clientLoan) {
        ClientDTO clientDTO = new ClientDTO(clientLoan.getClient());
        LoanDTO loanDTO = new LoanDTO(clientLoan.getLoan());
        id=clientLoan.getId();
        client = clientDTO;
        loan = loanDTO;
        payments = clientLoan.getPayments();
        amount = clientLoan.getAmount();
    }

    public Long getId() {
        return id;
    }

    public ClientDTO getClient() {
        return client;
    }

    public LoanDTO getLoan() {
        return loan;
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public void setLoan(LoanDTO loan) {
        this.loan = loan;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
