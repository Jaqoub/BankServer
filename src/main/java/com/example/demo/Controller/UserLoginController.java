package com.example.demo.Controller;

import com.example.demo.Logic.MailService;
import com.example.demo.Model.Accounts;
import com.example.demo.Model.TransActions;
import com.example.demo.Model.UserLogin;
import com.example.demo.Repositories.IAccountsRepository;
import com.example.demo.Repositories.ITransActions;
import com.example.demo.Repositories.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.*;

import static com.example.demo.Logic.GenerateRegistrationNumber.getAccountNumber;

public class UserLoginController {

    @Autowired
    UserLoginRepo userLoginRepo;
    @Autowired
    IAccountsRepository iAccountsRepository;
    @Autowired
    ITransActions iTransActions;
    @Autowired
    MailService mailService;

    private Long RegistrationNumber = 4444L;
    private HashMap<Integer, String> temporaryPassword = new HashMap<>();

    @PostMapping("/sendValidationCode")
    public ResponseEntity validateEmail(@RequestParam(name = "Email") String Email){
        Integer confirmEmailCode = generatePassword();
        mailService.sendemail(Email, "Welcome to Kea Bank, input these numbers:" + confirmEmailCode + "to, \nconfirm your Email");
        temporaryPassword.put(confirmEmailCode, Email);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserLogin> saveLogin(@RequestParam(name = "Fullname") String fullName,
                                               @RequestParam(name = "Username") String userName,
                                               @RequestParam(name = "passWord") String password,
                                               @RequestParam(name = "Cpr") String Cpr,
                                               @RequestParam(name = "address") String address,
                                               @RequestParam(name = "ConfirmationCode") Integer ConfirmationCode){
        List<TransActions>  transActions = new ArrayList<>();
        List<Accounts> accountsList = new ArrayList<>();

        if(temporaryPassword.get(ConfirmationCode).equals(userName)){
            accountsList.add(new Accounts("KeaBank", "Standard", 0, generateNumber(), RegistrationNumber, getAccountNumber(RegistrationNumber), transActions));
            accountsList.add(new Accounts("Budget", "Standard", 0, 0.0, RegistrationNumber, getAccountNumber(RegistrationNumber), transActions));
            UserLogin login = new UserLogin(fullName, Cpr, password, accountsList, userName);

            userLoginRepo.save(login);
            return new ResponseEntity<>(HttpStatus.OK);
        }

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("accountChecker")
    public ResponseEntity accountChecker(@RequestParam(name = "reg") Long reg, @RequestParam(name = "accountNumber") long accountNumber){
        Optional<Accounts> account = iAccountsRepository.findAllByRegistrationNumberAndAccountNumber(reg, accountNumber);

        if(account.isPresent()){
            return new ResponseEntity(HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/invalidLogin")
    public ResponseEntity invalidLogin(@RequestParam(name = "userName") String userName, @RequestParam(name = "passWord")String passWord) {
        Optional<UserLogin> user = userLoginRepo.findByEmailAndPassword(userName, passWord);

        if (user.isPresent()) {
            return new ResponseEntity(user, HttpStatus.OK);
        }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    public double generateNumber(){
        double start = 10000;
        double end = 37000;
        double random = new Random().nextDouble();
        double result = start + (random *(end - start));
        double generated = Double.valueOf(String.valueOf(new DecimalFormat("#.##").format(result).replace(",", ".")));


        return generated;
    }

    public Integer generatePassword(){
        Random ran = new Random();
        Integer generated = ran.nextInt(999999-100000);

        return generated;
    }
}
