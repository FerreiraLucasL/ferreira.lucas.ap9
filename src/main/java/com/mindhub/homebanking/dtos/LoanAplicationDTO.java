package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

public class LoanAplicationDTO {
    //axios.post("/api/loans", { loanId: this.loanTypeId, amount: this.amount, payments: this.payments, toAccountNumber: this.accountToNumber })
    private Long loanId;
    private Double amount;
    private int payments;
    private String toAccountNumber;

    public LoanAplicationDTO(Long loanId, Double amount, int payments, String toAccountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getAccountNumber() {
        return toAccountNumber;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public void setAccountNumber(String accountNumber) {
        this.toAccountNumber = accountNumber;
    }
}
