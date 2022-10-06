package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {


    public static void main(String[] args) {
        getData();
    }









    //get data from vendingmachine.csv
    public static void getData() {

        File inputFile = new File("vendingmachine.csv");
        //Products products = new Products();
        List<Products> itemList = new ArrayList<>();


        try (Scanner fileScanner = new Scanner(inputFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String temp[] = line.split("\\|");

                //temp[0] = itemCode temp[1] = productName, temp[2] = price
                int priceInPenny = (int)(Double.parseDouble(temp[2])*100);

                itemList.add(new Products(temp[0], temp[1], priceInPenny,5));


            }
            for(Products each: itemList) {
                System.out.println(each.getItemStock());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the file.");
        }

    }



}
