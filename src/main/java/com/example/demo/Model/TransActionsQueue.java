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
    private String texttoReciever;
    private Long FromaccountNumber;
    private Long FromregistrationNumber;
    private Long TocountNumber;
    private Long TOgistrationNumber;
    private double transactionAmount;
    private LocalDate date;
    private String text;
    private Long FaccN;
    private Long Freg;
    private Long TaccN;
    private Long Treg;
    private double amount;
    public TransActionsQueue(){


    }

    public TransActionsQueue(String transActionName, String texttoReciever, Long fromaccountNumber, Long fromregistrationNumber, String accountType, Long tocountNumber, Long TOgistrationNumber, double transactionAmmount, LocalDate date) {
        this.transActionName = transActionName;
        this.texttoReciever = texttoReciever;
        this.FromaccountNumber = fromaccountNumber;
        this.FromregistrationNumber = fromregistrationNumber;
        this.TocountNumber = tocountNumber;
        this.TOgistrationNumber = TOgistrationNumber;
        this.transactionAmount = transactionAmmount;
        this.date = date;
    }

    public TransActionsQueue(String transActionName, String text,
                             Long FaccN,Long Freg, Long Treg, Long TaccN,
                             double amount,
                             LocalDate date){
        this.transActionName = transActionName;
        this.text = text;
        this.FaccN = FaccN;
        this.Freg = Freg;
        this.Treg = Treg;
        this.amount = amount;
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

    public Long getFromaccountNumber() {
        return FromaccountNumber;
    }

    public void setFromaccountNumber(Long fromaccountNumber) {
        FromaccountNumber = fromaccountNumber;
    }

    public Long getFromregistrationNumber() {
        return FromregistrationNumber;
    }

    public void setFromregistrationNumber(Long fromregistrationNumber) {
        FromregistrationNumber = fromregistrationNumber;
    }

    public Long getTocountNumber() {
        return TocountNumber;
    }

    public void setTocountNumber(Long tocountNumber) {
        TocountNumber = tocountNumber;
    }

    public Long getTOgistrationNumber() {
        return TOgistrationNumber;
    }

    public void setTOgistrationNumber(Long TOgistrationNumber) {
        this.TOgistrationNumber = TOgistrationNumber;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmmount) {
        this.transactionAmount = transactionAmmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTexttoReciever() {
        return texttoReciever;
    }

    public void setTexttoReciever(String texttoReciever) {
        this.texttoReciever = texttoReciever;
    }
}
