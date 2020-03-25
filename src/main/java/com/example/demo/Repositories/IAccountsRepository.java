package com.example.demo.Repositories;

import com.example.demo.Model.Accounts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IAccountsRepository extends CrudRepository<Accounts, Long> {

    List<Accounts> findAll();
    Optional<Accounts> findAllByRegistrationNumberAndAccountNumber(Long reg, Long accountNumber);

}
