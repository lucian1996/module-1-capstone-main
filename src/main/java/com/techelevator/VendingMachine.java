package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    private static List<Products> itemList = new ArrayList<>();


    public static void main(String[] args) {
        getData();
    }

    //get data from VendingMachine.csv
    public static void getData() {
        File inputFile = new File("VendingMachine.csv");
//        Products products = new Products();
//        String finalString = "";
        try (Scanner fileScanner = new Scanner(inputFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String temp[] = line.split("\\|");
                //temp[0] = itemCode temp[1] = productName, temp[2] = price

                int priceInPenny = (int)(Double.parseDouble(temp[2])*100);
                itemList.add(new Products(temp[0], temp[1], priceInPenny,5));
//                finalString = temp[0] + temp[1] + temp[2];
            }
//            for(Products each: itemList) {
//                System.out.println(finalString);
//            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the file.");
        }
    }
    public static void printStock(){
        System.out.println(itemList.get(0));
    }
    public static void updateStock(){

    }
}
