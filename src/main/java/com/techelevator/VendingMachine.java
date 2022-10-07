package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class VendingMachine {
    private static List<Products> itemList = new ArrayList<>();
    private int currentMoneyProvided = 0;
    private static Balance balance = new Balance();
    private static Map<String, Products> productsForSale = new HashMap<>();

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
                productsForSale.put(temp[0], new Products(temp[0], temp[1], priceInPenny, 5));
                ;
                //
                //create a map, assign temp[0] to key, itemList(or Products class) as value.
                //


                
//                finalString = temp[0] + temp[1] + temp[2];
            }
            System.out.println(productsForSale.keySet());
            System.out.println(productsForSale.get("A1").getProductName());
//            for(Products each: itemList) {
//                System.out.println(finalString);
//            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the file.");
        }

        balance.setCurrentBalance(0);


    }

    public static void printStock() {
        for (int i = 0; i < itemList.size(); i++) {
            // A1 | product name | price || "SOLD OUT" if out of stock.
            if (itemList.get(i).getItemStock() == 0) {
                System.out.println("SOLD OUT");
            } else {
                String itemCode = itemList.get(i).getItemCode();
                String productName = itemList.get(i).getProductName();

                String money = dollarIntToString(itemList.get(i).getPrice());
                System.out.printf("%s | %s | $ %s%n", itemCode, productName, money);

            }
        }
    }

    public static void takeMoney() {
        Scanner scanMoney = new Scanner(System.in);
        System.out.print("Enter dollar bills please: ");
        String stringDollar = scanMoney.nextLine();
        balance.setCurrentBalance(balance.getCurrentBalance() + dollarStringToInt(stringDollar));
    }

    public static int currentBalanceAsStr() {

        return balance.getCurrentBalance();
    }

        public static void updateStock() {
    }

    public static String dollarIntToString(int dollarInInteger) {
        int dollar = dollarInInteger / 100;
        int penny = dollarInInteger % 100;  //1650 / 100 ->50 "0"


        return penny == 0 ? dollar + ".00" : dollar + "." + penny;


    }

    public static Integer dollarStringToInt(String dollarInString) {

        if (dollarInString.contains("\\.")) {
            String[] temp = dollarInString.split("\\.");
            return Integer.parseInt(temp[0]) * 100 + Integer.parseInt(temp[1]);
        } else {
            return Integer.parseInt(dollarInString) * 100;
        }
    }

    public static void purchaseItem() {
                Scanner purchaseScan = new Scanner(System.in);
                System.out.print("Enter Item Code: ");
                String purchaseItemCode = purchaseScan.nextLine();
                if (!(productsForSale.get(purchaseItemCode).getItemStock() == 0)) {
                    if (balance.getCurrentBalance() >= productsForSale.get(purchaseItemCode).getPrice()) {
                        balance.setCurrentBalance(balance.getCurrentBalance() - productsForSale.get(purchaseItemCode).getPrice());
                        productsForSale.get(purchaseItemCode).setItemStock(productsForSale.get(purchaseItemCode).getItemStock() - 1);
                    }
                }
        }
    }
