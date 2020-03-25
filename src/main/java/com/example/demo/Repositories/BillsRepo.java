package com.example.demo.Repositories;

import com.example.demo.Model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BillsRepo extends CrudRepository<Bill, Long> {
    Optional<Bill> findByObligatoryDigitsAndAccountNumberAndRegistrationNumber(String digits, Long account, Long reg);
    Optional<Bill> findByRegistrationNumberAndAccountNumber(Long reg, Long accountNumber);
}
