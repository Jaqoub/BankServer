package com.example.demo.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String account;
    private String accountName;
    private String accountType;
    private double currentDeposit;
    private long registrationNumber;
    private long accountNumber;
    private double transActionAmount;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<TransActions> transActions;

    public Accounts(){

    }


    //getTransactionAmount


    public Accounts(String account, String accountType, int i, double currentDeposit,
                    long registrationNumber, Long accountNumber, List<TransActions> transActions){
        this.account = account;
        this.accountType = accountType;
        this.accountName = accountName;
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

    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    public String getAccountName(){
        return accountName;
    }

    public double getTransActionAmount() {
        return transActionAmount;
    }

    public void setTransActionAmount(double transActionAmount) {
        this.transActionAmount = transActionAmount;
    }
}
