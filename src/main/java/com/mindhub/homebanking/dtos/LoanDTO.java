package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;
import java.util.Set;

public class LoanDTO {
    private Long id;
    private String name;
    private Double maxAmount;

    private Set<Integer> payments;

    public LoanDTO(Loan loan){
        id= loan.getId();
        name=loan.getName();
        maxAmount= loan.getMaxAmount();
        payments=loan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setPayments(Set<Integer> payments) {
        this.payments = payments;
    }
}
