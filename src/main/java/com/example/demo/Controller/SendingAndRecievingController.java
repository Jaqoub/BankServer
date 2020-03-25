package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Repositories.IAccountsRepository;
import com.example.demo.Repositories.BillsRepo;
import com.example.demo.Repositories.ITransactionsQueue;
import com.example.demo.Repositories.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import static  com.example.demo.Logic.GenerateRegistrationNumber.getAccountNumber;
import static com.example.demo.Logic.AgeCalculator.getAge;

@RestController
public class SendingAndRecievingController  {

    private Long registrationNumber = 4444L;

    @Autowired
    UserLoginRepo userLoginRepo;
    @Autowired
    ITransactionsQueue iTransactionsQueue;
    @Autowired
    IAccountsRepository iAccountsRepository;
    @Autowired
    BillsRepo billsRepo;

    @PostMapping("/sendmoneyToOtherAccount")
    public ResponseEntity<UserLogin> sendMoney(@RequestParam(name = "Email") String Email,
                                               @RequestParam(name = "transactionName") String transactionName,
                                               @RequestParam(name = "fromAccount") String Faccount,
                                               @RequestParam(name = "ToAccount") String toAccount,
                                               @RequestParam(name = "value") Double value,
                                               @RequestParam(name = "sendingOrRecieving") boolean sendingOrRecieving) {
        UserLogin user = userLoginRepo.findByEmail(Email);

        for (int i = 0; i<user.getAccountsList().size(); i++){
            if(user.getAccountsList().get(i).getAccount().equals(Faccount)){
                user.getAccountsList().get(i).getTransActions().add(new TransActions(transactionName, value, user.getAccountsList().get(i).getCurrentDeposit(), removeDecimals(user.getAccountsList().get(i).getCurrentDeposit()-value), LocalDate.now(), sendingOrRecieving));

                user.getAccountsList().get(i).setCurrentDeposit(removeDecimals(user.getAccountsList().get(i).getCurrentDeposit() - value));
                userLoginRepo.save(user);
            }

            if(user.getAccountsList().get(i).getAccount().equals(toAccount)){
                user.getAccountsList().get(i).getTransActions().add(new TransActions(transactionName,value,user.getAccountsList().get(i).getCurrentDeposit(),removeDecimals(user.getAccountsList().get(i).getCurrentDeposit()+value),LocalDate.now(),false));
                user.getAccountsList().get(i).setCurrentDeposit(removeDecimals(user.getAccountsList().get(i).getCurrentDeposit() + value));
                userLoginRepo.save(user);
            }
        }

        return new ResponseEntity(userLoginRepo.findByEmail(Email), HttpStatus.OK);

    }


    @GetMapping("/validateAge")
    public ResponseEntity getCostumerAge(@RequestParam(name = "Email")String Email){
        UserLogin userLogin = userLoginRepo.findByEmail(Email);

        if(getAge(userLogin.getCpr()) >= 77){
            System.out.println("Conditions are true");
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
    @PostMapping("/transaction")
    public void startTransactionsService(@RequestParam(name = "transationname") String transActionName,
                                         @RequestParam(name = "text",defaultValue = "")String text,
                                         @RequestParam(name = "Freg") Long Freg,
                                         @RequestParam(name = "FaccN") Long FaccN,
                                         @RequestParam(name = "TaccN") Long TaccN,
                                         @RequestParam(name = "Treg") Long Treg,
                                         @RequestParam(name = "amount") double amount){

        TransActionsQueue transactionsQueue =  new TransActionsQueue(transActionName, text, FaccN, Freg, TaccN,Treg,amount,LocalDate.now());
        iTransactionsQueue.save(transactionsQueue);
        List<TransActionsQueue> transQ = iTransactionsQueue.findByDateBeforeOrDate(LocalDate.now(), LocalDate.now());

        for (int i = 0; i < transQ.size(); i++){
            Optional<Accounts> fromAccount = iAccountsRepository.findAllByRegistrationNumberAndAccountNumber(transQ.get(i).getFromregistrationNumber(), transQ.get(i).getFromaccountNumber());
            Optional<Accounts> toAccount = iAccountsRepository.findAllByRegistrationNumberAndAccountNumber(transQ.get(i).getTOgistrationNumber(), transQ.get(i).getTocountNumber());
            Optional<Bill> billToPay = billsRepo.findByRegistrationNumberAndAccountNumber(transQ.get(i).getFromregistrationNumber(), transQ.get(i).getTocountNumber());

            toAccount.ifPresent(System.out::println);
            fromAccount.ifPresent(System.out::println);

            if(fromAccount.isPresent() && billToPay.isPresent()){
                fromAccount.get().getTransActions().add(new TransActions(billToPay.get().getNameOfBill(), "Message: " + transQ.get(i).getTexttoReciever(), transQ.get(i).getTransactionAmount(), fromAccount.get().getCurrentDeposit(), removeDecimals(fromAccount.get().getCurrentDeposit() - transQ.get(i).getTransactionAmount()), transQ.get(i).getDate(), true));
                fromAccount.get().setCurrentDeposit(removeDecimals(fromAccount.get().getCurrentDeposit() - transQ.get(i).getTransactionAmount()));
                iAccountsRepository.save(fromAccount.get());
                iTransactionsQueue.delete((TransActionsQueue) transQ.get(i));

                if(billToPay.get().getAmount() > ((TransActionsQueue) transQ.get(i)).getTransactionAmount()){
                    billToPay.get().setAmount(removeDecimals((billToPay.get().getAmount() - ((TransActionsQueue) transQ.get(i)).getTransactionAmount() + 100.0)));
                    billToPay.get().setNameOfBill(billToPay.get().getNameOfBill() + "Rykker");
                    billsRepo.save(billToPay.get());

                }

            } else if(fromAccount.isPresent() && toAccount.isPresent()){
                fromAccount.get().getTransActions().add(new TransActions("TO" + toAccount.get().getAccount(), "Message:" + transQ.get(i).getTexttoReciever(), transQ.get(i).getTransactionAmount(), fromAccount.get().getCurrentDeposit(), removeDecimals(fromAccount.get().getCurrentDeposit() - transQ.get(i).getTransactionAmount()), transQ.get(i).getDate(), true));
                fromAccount.get().setCurrentDeposit(removeDecimals(fromAccount.get().getCurrentDeposit() - transQ.get(i).getTransactionAmount()));
                iAccountsRepository.save(fromAccount.get());
                toAccount.get().getTransActions().add(new TransActions("From" + fromAccount.get().getAccount(), "Message:" + transQ.get(i).getTexttoReciever(), transQ.get(i).getTransactionAmount(), toAccount.get().getCurrentDeposit(), removeDecimals(toAccount.get().getCurrentDeposit() + transQ.get(i).getTransactionAmount()), transQ.get(i).getDate(), false));
                toAccount.get().setCurrentDeposit(removeDecimals(toAccount.get().getCurrentDeposit() + transQ.get(i).getTransactionAmount()));
                iAccountsRepository.save(toAccount.get());
                iTransactionsQueue.delete((TransActionsQueue) transQ.get(i));
            }
        }
    }

    @PostMapping("/sendtootherusers")
    public ResponseEntity sendtootherusers(@RequestParam(name = "Email") String email, @RequestParam(name = "Fromaccout") String fromAccount,@RequestParam(name = "Fromatype") String Fromatype, @RequestParam(name = "reg") Long reg, @RequestParam(name = "accountnb") Long accountNumber, @RequestParam(name = "amount") double amount, @RequestParam(name = "servicecode") String servicecode){
        Accounts accounts;
        UserLogin userLogin= userLoginRepo.findByEmail(email);
        for (int i = 0; i <userLogin.getAccountsList().size(); i++) {

            if(userLogin.getSendserviceCode().equals(servicecode) &&  userLogin.getAccountsList().get(i).getAccount().equals(fromAccount) && userLogin.getAccountsList().get(i).getAccountType().equals(Fromatype)){
                accounts  =userLogin.getAccountsList().get(i);
                iTransactionsQueue.save(new TransActionsQueue("From:" + fromAccount, "", accounts.getAccountNumber(), accounts.getRegistrationNumber(), accountNumber, reg, amount, LocalDate.now()));

            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/SaveSavingsAccount")
    public ResponseEntity createSavingsAccount(@RequestParam(name = "Email") String Email,
                                               @RequestParam(name = "accountName") String accountName,
                                               @RequestParam(name = "fromAccount") String fromAccount,
                                               @RequestParam(name = "accountType") String accountType,
                                               @RequestParam(name = "amount") double amount,
                                               @RequestParam(name = "date") String paymentDate,
                                               @RequestParam(name = "automaticSetting") String automaticSetting){


        List<TransActions> transActions = new ArrayList<>();
        UserLogin user = userLoginRepo.findByEmail(Email);
        Accounts savingsAccount = new Accounts(accountName, accountType, 0, 0.0, registrationNumber, getAccountNumber(registrationNumber), transActions);
        user.getAccountsList().add(savingsAccount);
        userLoginRepo.save(user);

        for (int i = 0; i < user.getAccountsList().size(); i++){
            if(user.getAccountsList().get(i).getAccount().equals(fromAccount)){
                System.out.println(automaticSetting);
                switch (automaticSetting){
                    case "Yearly":
                        System.out.println("Monthly Yearly");
                        automaticYears(amount, user.getAccountsList().get(i),savingsAccount);
                        break;

                    case "Monthly":
                        System.out.println("Monthly");
                        automaticMonth(amount, user.getAccountsList().get(i),savingsAccount);
                        break;

                    case "Weekly":
                        System.out.println("Weekly");
                        automaticWeek(amount, user.getAccountsList().get(i),savingsAccount);
                        break;

                    case "Daily":
                        System.out.println("Daily");
                        dailyPayment(amount, user.getAccountsList().get(i),savingsAccount);
                        break;

                    case "Date":
                        int day = Integer.valueOf(paymentDate.substring(9));
                        System.out.println(day);
                        int month = Integer.valueOf(paymentDate.substring(6,7));
                        System.out.println(month);
                        int year = Integer.valueOf(paymentDate.substring(0,4));
                        System.out.println(year);

                        LocalDate date = LocalDate.of(year, month, day + 1);
                        for (int j = 0; j < 10; j++){
                            payDate(amount, user.getAccountsList().get(j), savingsAccount, date);

                        }
                        break;
                }
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }


    private void automaticWeek(@RequestParam(name = "amount") double amount,
                               Accounts From, Accounts To){
        LocalDate today = LocalDate.now();
        System.out.println("Current Date: " + today);

        for (int i = 0; i < 12; i++){
            LocalDate weeks = today.plus(i, ChronoUnit.WEEKS);
            System.out.println(weeks + "weeks");
            iTransactionsQueue.save(new TransActionsQueue("From:" + From.getAccount(), "", From.getAccountNumber(), From.getRegistrationNumber(), To.getAccountType(), To.getAccountNumber(), To.getRegistrationNumber(), amount, weeks));
        }
    }

    private void dailyPayment(@RequestParam(name = "amount") double amount,
                              Accounts From,
                              Accounts To){

        LocalDate today = LocalDate.now();
        for (int i = 1; i < 30; i++){
            LocalDate weeks= today.plus(i, ChronoUnit.WEEKS);
            System.out.println(weeks + "days");
            iTransactionsQueue.save(new TransActionsQueue("From" + From.getAccountType(), "", From.getAccountNumber(), From.getRegistrationNumber(), To.getAccountType(), To.getAccountNumber(), To.getRegistrationNumber(), amount, weeks));
        }
    }


    private void automaticYears(@RequestParam(name = "amount") double amount, Accounts From,
                                Accounts To){
        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);
        for (int i = 0; i < 12; i++){
            LocalDate weeks = today.plus(i, ChronoUnit.YEARS);
            System.out.println(weeks.toString());
            iTransactionsQueue.save(new TransActionsQueue("From:" + From.getAccount(), "", From.getAccountNumber(), From.getRegistrationNumber(), To.getAccount(), To.getAccountNumber(), To.getRegistrationNumber(), amount, weeks));
        }
    }

    private void payDate(@RequestParam(name = "amount") double amount,
                         Accounts From,
                         Accounts To,
                         LocalDate localDate){
        iTransactionsQueue.save(new TransActionsQueue("From:" + From.getAccount(), "", From.getAccountNumber(), From.getRegistrationNumber(), To.getAccount(), To.getAccountNumber(), To.getRegistrationNumber(), amount, localDate));
    }

    @GetMapping("/getCpr")
    public ResponseEntity<UserLogin>getCpr(@RequestParam(name = "Email") String Email){
        UserLogin userLogin = new UserLogin(userLoginRepo.findByEmail(Email).getCpr());

        return new ResponseEntity<>(userLogin, HttpStatus.OK);
    }


    private double removeDecimals(double amount){
        double generated =  Double.parseDouble(String.valueOf(new DecimalFormat("#,##").format(amount).replace(",",".")));
        return generated;
    }

    private void automaticMonth(@RequestParam(name = "ammount") double ammount, Accounts From, Accounts TO) {


        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);
        //add 2 week to the current date

        for (int i = 1; i < 12; i++) {
            LocalDate weeaks = today.plus(i, ChronoUnit.MONTHS);
            System.out.println(weeaks + " months");
            iTransactionsQueue.save(new TransActionsQueue("From:" + From.getAccount(),"", From.getAccountNumber(), From.getRegistrationNumber(), TO.getAccountNumber(), TO.getRegistrationNumber(), ammount, weeaks));
        }


    }

}



