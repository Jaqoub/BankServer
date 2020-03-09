package com.example.demo.Controller;

import com.example.demo.Model.Bill;
import com.example.demo.Model.TransActionsQueue;
import com.example.demo.Model.UserLogin;
import com.example.demo.Repositories.ITransactionsQueue;
import com.example.demo.Repositories.UserLoginRepo;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TransferQueue;

@RestController
public class BillsController {
    @Autowired
    BillsRepo billsRepo;
    @Autowired
    UserLoginRepo userLoginRepo;
    @Autowired
    ITransactionsQueue iTransactionsQueue;

    @PostMapping("/checkIfBillExist")
    public ResponseEntity getBill(@RequestParam(name = "digits") String obligatoryDigits, @RequestParam(name = "accountnumber") Long accountNumber,
                                  @RequestParam(name = "registrationNumber") Long registrationNumber) {
        System.out.println(obligatoryDigits + " " + accountNumber + " " + "" + registrationNumber);
        Optional<Bill> bills = billsRepo.findByObligatoryDigitsAndAccountNumberAndRegistrationNumber("+" + obligatoryDigits, accountNumber, registrationNumber);


            if(bills.isPresent()){
                System.out.println("bill exists");
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

    @GetMapping("/fakebills")
    public ResponseEntity createFakeBill(){
        Bill bills = new Bill("Dong-energy", 100.0, "+71", 10203040506070L, 10203014L);
        Bill bills1 = new Bill("3Mobil",350.0,"+71",203040506070L,10203015L);
        Bill bills2= new Bill("Asb-bolig",9764.0,"+71",304050607080L,10203016L);
        Bill bills3 = new Bill("parkio",750.0,"+71",405060708090L,10203017L);
        Bill bills4 = new Bill("radius",950.0,"+71",506070809000L,10203018L);

        billsRepo.save(bills);
        billsRepo.save(bills1);
        billsRepo.save(bills2);
        billsRepo.save(bills3);
        billsRepo.save(bills4);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/paybill")
    public ResponseEntity<Bill> paybills(@RequestParam(name = "Email") String Email,
                                         @RequestParam(name = "TransactionName") String Transaction,
                                         @RequestParam(name = "fromAccount") String Faccount,
                                         @RequestParam(name = "date") String date){

        System.out.println(date);
        UserLogin userLogin = userLoginRepo.findByEmail(Email);
        for (int i = 0; i < userLogin.getAccountsList().size(); i++){

            if(userLogin.getSendserviceCode().equals(serviceCode) && userLogin.getAccountsList().get(i).getAccount().equals(Faccount)){
                System.out.println("this is true");

                if(pbs){
                    LocalDate fromTheFirstOfMonth = LocalDate.of(Integer.valueOf(date.substring(6)), Integer.valueOf(date.substring(4, 5)),1);
                    for (int j = 1; j < 12; j++){

                        LocalDate payBillInTheFuture = fromTheFirstOfMonth.plus(j, ChronoUnit.MONTHS);
                        TransActionQueue transferQueue = new TransferQueue(transActionName, "", userLogin.getAccountsList().get(i).getAccountNumber(), userLogin.getAccountsList().get(i).getRegistrationNumber(), accountNumbe, registrationNumber, value, payBillInTheFuture);
                        iTransactionsQueue.save(transferQueue);
                    }
                    }
                 TransActionsQueue transActionsQueue = new TransferQueue(tranceActionName, "", userLogin.getAccountsList().getAccountNumber(), userLogin.getAccountsList().getRegistrationNumber(), accountNumber, registrationNumber, value, returnFormatDate(date));
                    iTransactionsQueue.save(transActionsQueue);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    public LocalDate returnFormatDate(String date){
        int day = Integer.valueOf(date.substring((0,2));
        System.out.println(day);
        int month = Integer.valueOf(date.substring(4,5));
        System.out.println(month);
        int year = Integer.valueOf(date.substring(6));
        System.out.println(year);
        LocalDate localDate = LocalDate.of(year, month, day+1);
    }


}


