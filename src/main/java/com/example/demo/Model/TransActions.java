package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class TransActions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transactionName;
    private String Email;
    private String Faccount;
    private String toAccount;
    private double value;
    private String textReciever;
    private double depositBeforeTransaction;
    private double depositAfterTransaction;
    private LocalDate date;
    private String time;
    private boolean sendingOrRecieving;

    public TransActions(String transactionName, Double value, double currentDeposit, double v, LocalDate now, boolean sendingOrRecieving){}

    public TransActions(String transActionName, String s, Double value, double currentDeposit, double v, LocalDate now, boolean b) {
    }

    public TransActions(Long id, String transactionName, String textReciever, double depositBeforeTransaction,
                        double depositAfterTransaction, LocalDate date, String time,
                        boolean sendingOrRecieving) {
        this.id = id;
        this.transactionName = transactionName;
        this.textReciever = textReciever;
        this.depositBeforeTransaction = depositBeforeTransaction;
        this.depositAfterTransaction = depositAfterTransaction;
        this.date = date;
        this.time = time;
        this.sendingOrRecieving = sendingOrRecieving;
    }

    public TransActions(String Email, String transactionName,
                        String Faacount, String toAccount, double value){
    this.Email = Email;
    this.transactionName = transactionName;
    this.Faccount = Faccount;
    this.toAccount = toAccount;
    this.value = value;
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTextReciever() {
        return textReciever;
    }

    public void setTextReciever(String textReciever) {
        this.textReciever = textReciever;
    }

    public double getDepositBeforeTransaction() {
        return depositBeforeTransaction;
    }

    public void setDepositBeforeTransaction(double depositBeforeTransaction) {
        this.depositBeforeTransaction = depositBeforeTransaction;
    }

    public double getDepositAfterTransaction() {
        return depositAfterTransaction;
    }

    public void setDepositAfterTransaction(double depositAfterTransaction) {
        this.depositAfterTransaction = depositAfterTransaction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSendingOrRecieving() {
        return sendingOrRecieving;
    }

    public void setSendingOrRecieving(boolean sendingOrRecieving) {
        this.sendingOrRecieving = sendingOrRecieving;
    }
}
