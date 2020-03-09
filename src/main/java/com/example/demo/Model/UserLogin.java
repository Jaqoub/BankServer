package com.example.demo.Model;

import javax.persistence.*;
import java.util.List;
@Entity
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String Cpr;
    private String email;
    private String password;
    private String sendserviceCode;
    @OneToMany(cascade = {CascadeType.ALL})
    List<Accounts> accountsList;

    public UserLogin() {

    }

    public UserLogin(String fullName, String email,String Cpr, String password, List<Accounts> accountsList) {
        this.fullName = fullName;
        this.email = email;
        this.Cpr=Cpr;
        this.password = password;
        this.accountsList = accountsList;
    }

    public UserLogin(String fullName, String cpr, String email, String password, String sendserviceCode, List<Accounts> accountsList) {
        this.fullName = fullName;
        Cpr = cpr;
        this.email = email;
        this.password = password;
        this.sendserviceCode = sendserviceCode;
        this.accountsList = accountsList;
    }

    public UserLogin(String cpr) {
        Cpr = cpr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Accounts> getAccountsList() {
        return accountsList;
    }

    public void setAccountsList(List<Accounts> accountsList) {
        this.accountsList = accountsList;
    }

    public String getCpr() {
        return Cpr;
    }

    public void setCpr(String cpr) {
        Cpr = cpr;
    }

    public String getSendserviceCode() {
        return sendserviceCode;
    }

    public void setSendserviceCode(String sendserviceCode) {
        this.sendserviceCode = sendserviceCode;
    }
}









