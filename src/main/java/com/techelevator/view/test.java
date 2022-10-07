package com.techelevator.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class test {


    public static void main(String[] args) {

        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        System.out.println(localTime.format(dateTimeFormatter));

        System.out.println(LocalDate.now());



    }
}
