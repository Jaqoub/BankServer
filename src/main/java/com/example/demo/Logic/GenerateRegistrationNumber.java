package com.example.demo.Logic;

import java.util.Random;

public class GenerateRegistrationNumber {

    public static Long getAccountNumber(Long registrationNumber){
        long x = 999999L;
        long y = 100000L;
        Random r = new Random();
        long number = x+((long)(r.nextDouble()*(y-x)));
        String putRegistrationNr = String.valueOf(registrationNumber) + number;

        return Long.parseLong(putRegistrationNr);
    }
}
