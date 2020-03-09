package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class TransActionsQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transActionName;
    private String textToReciever;
    private Long fromAccountNumber;
    private Long fromRegistrationNumber;
    private Long toAccountNumber;
    private double transActionAmount;
    private LocalDate date;

    public TransActionsQueue(){}

    public TransActionsQueue(String transActionName,
                             String textToReciever,
                             Long fromAccountNumber,
                             Long fromRegistrationNumber,
                             Long toAccountNumber,
                             double transActionAmount,
                             LocalDate date){
       this.transActionName = transActionName;
       this.fromAccountNumber = fromAccountNumber;
       this.fromRegistrationNumber = fromRegistrationNumber;
       this.toAccountNumber = toAccountNumber;
       this.transActionAmount = transActionAmount;
       this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransActionName() {
        return transActionName;
    }

    public void setTransActionName(String transActionName) {
        this.transActionName = transActionName;
    }

    public String getTextToReciever() {
        return textToReciever;
    }

    public void setTextToReciever(String textToReciever) {
        this.textToReciever = textToReciever;
    }

    public Long getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(Long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public Long getFromRegistrationNumber() {
        return fromRegistrationNumber;
    }

    public void setFromRegistrationNumber(Long fromRegistrationNumber) {
        this.fromRegistrationNumber = fromRegistrationNumber;
    }

    public Long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(Long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public double getTransActionAmount() {
        return transActionAmount;
    }

    public void setTransActionAmount(double transActionAmount) {
        this.transActionAmount = transActionAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
