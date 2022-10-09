package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class VendingMachineCLITest extends TestCase {
    private VendingMachine sut;
    private File inputFile;
    @Before
    public void Setup(){
        sut = new VendingMachine();
        inputFile = new File("VendingMachine.csv");
        try (Scanner fileScanner = new Scanner(inputFile)) {
            sut.getData(fileScanner);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the file.");
        }
    }
    @Test
    public void shouldDisplayItemList() {
        List<String> actual = sut.getItemCodeList();
        Assert.assertArrayEquals(new String[]{"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4"}, actual);
    }
    public void shouldPurchaseMenu() {
        sut.getProductsForSale();
        Assert.assertEquals();
    }
    public void shouldExit() {

    }



}