package com.example.demo.Controller;

import com.example.demo.Repositories.ITransactionsQueue;
import com.example.demo.Repositories.UserLoginRepo;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
