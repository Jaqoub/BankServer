package com.example.demo.Logic;

import java.time.LocalDate;
import java.time.Period;

import static java.lang.String.valueOf;

public class AgeCalculator {

    public static int getAge(String Cpr){
        LocalDate ld = LocalDate.of(Integer.valueOf("19" + Cpr.substring(4,6)), Integer.valueOf(Cpr.substring(2, 4)), Integer.valueOf(Cpr.substring(0, 2)));
        LocalDate now = LocalDate.now();
        Period diff = Period.between(ld, now);
        System.out.println(diff.getYears() + "Years" + diff.getMonths()+ "Months" + diff.getDays() + "Days");

        return diff.getYears();
    }
}
