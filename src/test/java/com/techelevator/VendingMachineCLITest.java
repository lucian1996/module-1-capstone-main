package com.techelevator;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineCLITest {
    private VendingMachine sut;
    private File inputFile;
    private Balance balSut;

    @Before
    public void Setup(){
        sut = new VendingMachine();
        balSut = new Balance();
    }
    @Test
    public void menu_should_display_item_list() {

        List<String> actual = sut.getItemCodeList();
        List<String> expected = new ArrayList<>();
        expected.add("A1");
        expected.add("A2");
        expected.add("A3");
        expected.add("A4");
        expected.add("B1");
        expected.add("B2");
        expected.add("B3");
        expected.add("B4");
        expected.add("C1");
        expected.add("C2");
        expected.add("C3");
        expected.add("C4");
        expected.add("D1");
        expected.add("D2");
        expected.add("D3");
        expected.add("D4");

        for (int i = 0; i < actual.size(); i ++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }


    @Test
    public void dollar_Int_To_String_165_to_1_65() {
        String expected = "1.65";
        String actual = sut.dollarIntToString(165);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void dollar_Int_To_String_1_to_0_01() {
        String expected = "0.01";
        String actual = sut.dollarIntToString(1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void dollar_String_To_Int_1999_to_19_99() {
        int expected = 199;
        int actual = sut.dollarStringToInt("1.99");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void dollar_String_To_Int_whole_dollar_to_int() {
        int expected = 1000;
        int actual = sut.dollarStringToInt("10");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void take_money_current_10_add_5_total_15() {

        balSut.setBalance(1000);
        String userInput = "5";
        balSut.setBalance(balSut.getBalance() + sut.dollarStringToInt(userInput));
        int actual = balSut.getBalance();
        int expected = 1500;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void take_money_current_0_add_9_total_9() {

        balSut.setBalance(0);
        String userInput = "9";
        balSut.setBalance(balSut.getBalance() + sut.dollarStringToInt(userInput));
        int actual = balSut.getBalance();
        int expected = 900;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void take_money_should_decline_negative_integer(){
        balSut.setBalance(0);
        String userInput = "-9";
        if (sut.dollarStringToInt(userInput) < 0){
            System.out.println("Cannot Deposit negative amount");
        } else if (sut.dollarStringToInt(userInput) > 500000) {
            System.out.println("Cannot Deposit more than $5000");
        } else {
            balSut.setBalance(balSut.getBalance() + sut.dollarStringToInt(userInput));
        }
        int actual = balSut.getBalance();
        int expected = 0;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void take_money_should_decline_9000() {
        balSut.setBalance(0);
        String userInput = "9000";
        if (sut.dollarStringToInt(userInput) < 0){
            System.out.println("Cannot Deposit negative amount");
        } else if (sut.dollarStringToInt(userInput) > 500000) {
            System.out.println("Cannot Deposit more than $5000");
        } else {
            balSut.setBalance(balSut.getBalance() + sut.dollarStringToInt(userInput));
        }
        int actual = balSut.getBalance();
        int expected = 0;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchase_item_should_accept_B1() {




    }

    @Test
    public void purchase_item_should_decline_G4() {
        boolean actual = false;
        boolean expected = false;
        String userInput = "G4";
        if (sut.getItemCodeList().contains(userInput)) {
            actual = true;
        }
        Assert.assertEquals(expected, actual);
    }




}