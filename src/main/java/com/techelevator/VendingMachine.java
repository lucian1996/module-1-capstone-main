package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    private static List<Products> itemList = new ArrayList<>();


//    public static void main(String[] args) {
//        getData();
//    }

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

                int priceInPenny = (int) (Double.parseDouble(temp[2]) * 100);
                itemList.add(new Products(temp[0], temp[1], priceInPenny, 5));
//                finalString = temp[0] + temp[1] + temp[2];
            }
//            for(Products each: itemList) {
//                System.out.println(finalString);
//            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the file.");
        }
    }

    public static void printStock() {
        for (int i = 0; i < itemList.size(); i++) {
            // A1 | product name | price || "SOLD OUT" if out of stock.
            if (itemList.get(i).getItemStock() == 0) {
                System.out.println("SOLD OUT");
            } else {
                String itemCode = itemList.get(i).getItemCode();
                String productName = itemList.get(i).getProductName();
                int dollar = itemList.get(i).getPrice() / 100;
                int penny = itemList.get(i).getPrice() % 100;
                //work on spacing for better look?
                System.out.printf("%s | %s | $ %s.%s%n", itemCode, productName, dollar, penny);
            }
        }
    }

    public static void takeMoney() {
        double currentMoneyProvided = 0;
        Scanner scanMoney = new Scanner(System.in);
        System.out.println("Enter dollar bills please: ");
        String stringDollar = scanMoney.nextLine();
        double centsDollar = Double.parseDouble(stringDollar);
        currentMoneyProvided += centsDollar;
        System.out.println(currentMoneyProvided);
    }

    public static void takeOrder() {
        Scanner scanOrder = new Scanner(System.in);
        System.out.println("Enter Product Code: ");
        String userProductCode = scanOrder.nextLine();
        if (userProductCode == VendingMachine.getData(itemList.contains(userProductCode)));
        System.out.println(userProductCode);
    }


        public static void updateStock() {

    }
}
