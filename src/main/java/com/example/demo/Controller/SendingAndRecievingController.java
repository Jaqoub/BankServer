package com.example.demo.Controller;

import com.sun.org.apache.regexp.internal.RE;
import com.example.demo.Model.Accounts;
import com.example.demo.Model.TransActions;
import com.example.demo.Model.TransActionsQueue;
import com.example.demo.Model.UserLogin;
import com.example.demo.Repositories.BillsRepo;
import com.example.demo.Repositories.ITransactionsQueue;
import com.example.demo.Repositories.UserLoginRepo;
import net.bytebuddy.asm.Advice;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TransferQueue;
import javax.persistence.criteria.From;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@RestController
public class SendingAndRecievingController  {

    private Long registrationNumber = 4444L;

    @Autowired
    UserLoginRepo userLoginRepo;
    @Autowired
    ITransactionsQueue iTransactionsQueue;
    @Autowired
    IaccountsRepository iaccountsRepository;
    @Autowired
    BillsRepo billsRepo;

    @PostMapping("/sendmoneyToOtherAccount")
    public ResponseEntity<UserLogin> sendMoney(@RequestParam(name = "Email") String Email,
                                               @RequestParam(name = "TransActionName") String TransActionName,
                                               @RequestParam(name = "fromAccount") String Faccount,
                                               @RequestParam(name = "ToAccount") String toAccount,
                                               @RequestParam(name = "value") Double value,
                                               @RequestParam(name = "sendingOrRecieving") boolean sendingOrRecieving) {
        UserLogin user = userLoginRepo.findByEmail(Email);

        for(int i = 0; i < user.getAccountsList().size(); i++){
            if(user.getAccountsList().get(i).getAccountNumber().equals(Faccount)){
                user.getAccountsList().get(i).getTransActions().add(new TransActions(TransActionName, value, user.getAccountsList().get(i).getCurrentDeposit(), removedecimals(user.getAccountsList().get(i).getCurrentDeposit()-value), LocalDate.now(), sendingOrRecieving));
                user.getAccountsList().get(i).setCurrentDeposit(removeDecimals(user.getAccountsList().get(i).getCurrentDeposit()-value));
                userLoginRepo.save(user);
            }

            if(user.getAccountsList().get(i).getAccount().equals(toAccount)){
                user.getAccountsList().get(i).getTransActions().add(new TransActions(TransActionName, value, user.getAccountsList().get(i).getCurrentDeposit(), removeDecimals(user.getAccountsList().get(i).getCurrentDeposit()+value), LocalDate.now(), false));
                user.getAccountsList().get(i).setCurrentDeposit(removeDecimals(user.getAccountsList().get(i).getCurrentDeposit()+value));
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
    public void startTransActionService(@RequestParam(name = "transActionName") String transActionName,
                                        @RequestParam(name = "text",defaultValue = "")String text,
                                        @RequestParam(name = "Freg") Long Freg,
                                        @RequestParam(name = "FaccN") Long FaccN,
                                        @RequestParam(name = "TaccN") Long TaccN,
                                        @RequestParam(name = "Treg") Long Treg,
                                        @RequestParam(name = "amount") double amount){
        TransActionsQueue transActionsQueue = new TransActionsQueue(transActionName, text, FaccN, Freg, Treg, amount, LocalDate.now());
        ITransactionsQueue.save(transActionsQueue);
        List<TransferQueue> transQ = iTransactionsQueue.findByDateBeforeOrDate(LocalDate.now(), LocalDate.now());

        for (int i = 0; i < transQ.size(); i++){
            Optional<Accounts> fromAccount = iaccountsRepository.findAllByRegistrationAndAccountNumber(transQ.get(i).getFromRegistrationNumber(), transQ.get(i).getFromAccountNumber());
            Optional<Accounts> toAccount = iaccountsRepository.findAllByRegistrationAndAccountNumber(transQ.get(i).getRegistrationNumber(), transQ.get(i).getToAccountNumber());
            Optional<Accounts> billToPay = billsRepo.findByRegistrationNumberAndAccountNumber(transQ.get(i).getRegistrationNumber(), transQ.get(i).getToAccountNumber());

            toAccount.ifPresent(System.out::println);
            fromAccount.ifPresent(System.out::println);

            if(fromAccount.isPresent() && billToPay.isPresent()){
                fromAccount.get().getTransActions().add(new TransActions(billToPay.get().getNameOfBill(), "Message: " + transQ.get(i).getTextToReciever()); transQ.get(i).getTransActionAmount(), fromAccount.get().getCurrentDeposit(), removeDecimals(fromAccount.get().getCurrentDeposit() - transQ.getTransActionAmount(), transQ.get(i).getDate(), true));
                fromAccount.get().setCurrentDeposit(removeDecimals(fromAccount.get().getCurrentDeposit() - transQ.get(i).getTransactionAmount()));
                iaccountsRepository.save(fromAccount.get());
                iTransactionsQueue.delete(transQ.get(i));

                if(billToPay.get().getAmount() > transQ.get(i).getTransactionAcmount()){
                    billToPay.get().setAmount(removeDecimals((billToPay.get().getAmount() - transQ.get(i).getTransactionAmount() + 100.0)));
                    billToPay.get().setNameOfBill(billToPay.get().getNameOfBill() + "Rykker");
                    billsRepo.save(billToPay.get());

                }

            } else if(fromAccount.isPresent() && toAccount.isPresent()){
                fromAccount.get().getTransActions().add(new TransActions("TO" + toAccount.get().getAccount(), "Message:" + transQ.get(i).getTextToReciever(), transQ.get(i).getTransActionAmount(), fromAccount.get().getCurrentDeposit(), removeDecimals(fromAccount.get().getCurrentDeposit() - transQ.get(i).getTransActionAmount(), transQ.get(i).getDate(), true));
                fromAccount.get().setCurrentDeposit(removeDecimals(fromAccount.get().getCurrentDeposit() - transQ.get(i).getTransActionAmount()));
                iaccountsRepository.save(fromAccount.get());
                toAccount.get().getTransActions().add(new TransActions("From" + fromAccount.get().getAccount(), "Message:" + transQ.get(i).getTextToReciever(), transQ.get(i).getTransActionAmount(), toAccount.get().getCurrentDeposit(), removeDecimals(toAccount.get().getCurrentDeposit() + transQ.get(i).getTransActionAmount(), transQ.get(i).getDate(), false));
                toAccount.get().setCurrentDeposit(removeDecimals(toAccount.get().getCurrentDeposit() + transQ.get(i).getTransActionAmount()));
                iaccountsRepository.save(toAccount.get());
                iTransactionsQueue.delete(transQ.get(i));
            }
        }
    }

    @PostMapping("/sendToTheOtherUsers")
    public ResponseEntity sendToTheOtherUsers(@RequestParam(name = "Email") String Email, @RequestParam(name = "fromAccount") String fromAccount, @RequestParam(name = "fromType") String fromType, @RequestParam(name = "reg") Long reg, @RequestParam(name = "accountNumber") Long accountNumber, @RequestParam(name = "amount") double amount, @RequestParam(name = "serviceCode") String serviceCode){
        Accounts accounts;
        UserLogin userLogin = userLoginRepo.findByEmail(Email);
        for (int i = 0; i < userLogin.getAccountsList().size(); i++){
            if(userLogin.getSendserviceCode().equals(serviceCode) && userLogin.getAccountsList().get(i).getAccount().equals(fromAccount)&& userLogin.getAccountsList().get(i).getAccountType().equals(fromType)){
                accounts = userLogin.getAccountsList().get(i);
                iTransactionsQueue.save(new TransActionsQueue("From:" + fromAccount +  "", accounts.getAccountNumber(), accounts.getRegistrationNumber(), accountNumber, reg, amount, LocalDate.now()));
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
        Accounts savingsAccount = new Accounts(accountName, accountType, 0.0, registrationNumber, getAccountNumber(registrationNumber), transActions);
        user.getAccountsList().add(savingsAccount);
        userLoginRepo.save(user);

        for (int i = 0; i < user.getAccountsList().size(); i++){
            if(user.getAccountsList().get(i).getAccount().equals(fromAccount)){
                System.out.println(automaticSetting);
                switch (automaticSetting){
                    case "Yearly":
                        System.out.println("Monthly Yearly");
                        automaticYears(amount, user.getAccountsList().get(i).savingsAccount);
                        break;

                    case "Monthly":
                        System.out.println("Monthly");
                        automaticMonth(amount, user.getAccountsList().get(i).savingsAccount);
                        break;

                    case "Weekly":
                        System.out.println("Weekly");
                        automaticWeek(amount, user.getAccountsList().get(i).savingsAccount);
                        break;

                    case "Daily":
                        System.out.println("Daily");
                        automaticDay(amount, user.getAccountsList().get(i).savingsAccount);
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



}



