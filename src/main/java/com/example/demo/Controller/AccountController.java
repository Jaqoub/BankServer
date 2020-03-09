package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Model.Accounts;
import com.example.demo.Model.TransActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Repositories.UserLoginRepo;
import com.example.demo.Repositories.ITransactionsQueue;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import static com.example.demo.Logic.GenerateRegistrationNumber.getAccountNumber;
@RestController
public class AccountController {
    @Autowired
    UserLoginRepo userLoginRepo;
    @Autowired
    ITransactionsQueue iTransactionsQueue;
    @Autowired
    IAccountRepository iAccountRepository;

    private Long RegistrationNumber = 4444;

    @PostMapping("/newAccount")
    public ResponseEntity<String> newAccount(@RequestParam(name = "Email") String Email, @RequestParam(name = "Accountname") String accountname, @RequestParam(name = "AccountType") String AccountType){
        List<TransActions> transActions = new ArrayList<>();
        UserLogin user = userLoginRepo.findByEmail(Email);
        Accounts account = new Accounts(accountname, AccountType, 0,0, RegistrationNumber, getAccountNumber(RegistrationNumber), transActions);
        user.getAccountsList().add(account);
        userLoginRepo.save(user);

        return new ResponseEntity<>("senior", HttpStatus.OK);
    }


    @GetMapping("/getAccounts")
    public ResponseEntity<UserLogin> getAccounts(@RequestParam(name = "Email") String Email){
        UserLogin user= userLoginRepo.findByEmail(Email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<Iterable<UserLogin>> getAllAccounts(){
        Iterable<UserLogin> user = userLoginRepo.findAll();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/AccountTransfers")
    public ResponseEntity getAllAccountTransactions(@RequestParam(name = "Email") String Email, @RequestParam(name = "Accountname") String AccountName){
        UserLogin user = userLoginRepo.findByEmail(Email);
        Accounts transActions;

        for(int i = 0; i < user.getAccountsList().size(); i++){
            if(user.getAccountsList().get(i).getAccount().equals(AccountName)){
                transActions = user.getAccountsList().get(i);
                return new ResponseEntity<>(transActions, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getDate")
    public void date(){
        for(int j = 4; j < 12; j++){
            String dateNow = "yyyy-MM-dd";
            LocalDate l = LocalDate.of(2020, j, 1);

            SimpleDateFormat simpleDate = new SimpleDateFormat(dateNow);
            String date = simpleDate.format(l.toString());
            System.out.println(date);
        }
    }


    @GetMapping("/getCurrentDeposit")
public ResponseEntity getDeposit(@RequestParam(name = "Email") String Email, @RequestParam(name = "account") String account){
        UserLogin userLogin = userLoginRepo.findByEmail(Email);

        return new ResponseEntity(userLogin, HttpStatus.OK);
    }

    public String dateDifference(int year, int month, int day){
        LocalDate l = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(now, l);

        return diff.toString();
    }

}
