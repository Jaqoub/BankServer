package com.example.demo.Repositories;

import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.Model.UserLogin;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepo extends CrudRepository<UserLogin, Long> {
    Optional<UserLogin> findByEmailAndPassWord(String Email, String passWord);
    UserLogin findByEmail(String Email);
    List<UserLogin> findAll();

    Optional<UserLogin> findByEmailAndSendServiceCode(String Email, String serviceCode);
}
