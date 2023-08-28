package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;
    private Long loanId;
    private String name;
    private Integer payments;
    private Double amount;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id= clientLoan.getId();
        loanId= clientLoan.getLoan().getId();
        name=clientLoan.getLoan().getName();
        payments= clientLoan.getPayment();
        amount= clientLoan.getAmount();
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public Integer getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }
}
