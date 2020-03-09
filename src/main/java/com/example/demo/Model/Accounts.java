package com.example.demo.Model;

import javax.persistence.*;
import javax.transaction.Transaction;
import java.util.List;

@Entity
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String account;
    private String accountType;
    private double currentDeposit;
    private long registrationNumber;
    private long accountNumber;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<TransActions> transActions;

    public Accounts(){

    }

    public Accounts(String account, String accountType, double currentDeposit,
                    long registrationNumber, Long accountNumber, List<TransActions> transActions){
        this.account = account;
        this.accountType = accountType;
        this.currentDeposit = currentDeposit;
        this.registrationNumber = registrationNumber;
        this.accountNumber = accountNumber;
        this.transActions = transActions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getCurrentDeposit() {
        return currentDeposit;
    }

    public void setCurrentDeposit(double currentDeposit) {
        this.currentDeposit = currentDeposit;
    }

    public long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<TransActions> getTransActions() {
        return transActions;
    }

    public void setTransActions(List<TransActions> transActions) {
        this.transActions = transActions;
    }
}
