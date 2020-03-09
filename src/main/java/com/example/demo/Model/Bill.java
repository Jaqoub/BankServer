package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

    @Entity
    public class Bill {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String nameOfBill;
        private Double amount;
        private String obligatoryDigits;
        private Long accountNumber;
        private Long registrationNumber;

        public Bill(String obligatoryDigits, Long accountNumber, Long registrationNumber){
            this.obligatoryDigits = obligatoryDigits;
            this.accountNumber = accountNumber;
            this.registrationNumber = registrationNumber;
        }

        public Bill(String nameOfBill, Double amount, String obligatoryDigits, Long accountNumber, Long registrationNumber) {
            this.nameOfBill = nameOfBill;
            this.amount = amount;
            this.obligatoryDigits = obligatoryDigits;
            this.accountNumber = accountNumber;
            this.registrationNumber = registrationNumber;
        }

        public Bill(){

        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNameOfBill() {
            return nameOfBill;
        }

        public void setNameOfBill(String nameOfBill) {
            this.nameOfBill = nameOfBill;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getObligatoryDigits() {
            return obligatoryDigits;
        }

        public void setObligatoryDigits(String obligatoryDigits) {
            this.obligatoryDigits = obligatoryDigits;
        }

        public Long getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(Long accountNumber) {
            this.accountNumber = accountNumber;
        }

        public Long getRegistrationNumber() {
            return registrationNumber;
        }

        public void setRegistrationNumber(Long registrationNumber) {
            this.registrationNumber = registrationNumber;
        }


}
