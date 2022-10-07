package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class VendingMachine {
    private List<String> itemCodeList = new ArrayList<>();
    private int currentMoneyProvided = 0;
    private Balance balance = new Balance();
    private Map<String, Products> productsForSale = new HashMap<>();
    private String userItemCode;

    //get data from VendingMachine.csv
    public void getData(Scanner fileScanner) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String temp[] = line.split("\\|");
                //temp[0] = itemCode temp[1] = productName, temp[2] = price

                int priceInPenny = (int) (Double.parseDouble(temp[2]) * 100);
                itemCodeList.add(temp[0]);

                // Creating a Map<item code, Products class>
                //                 item code              category  name      price       # in stock
                productsForSale.put(temp[0], new Products(temp[3], temp[1], priceInPenny, 5));
            }
        balance.setCurrentBalance(0);
    }

    public void takeMoney() {
        Scanner scanMoney = new Scanner(System.in);
        String stringDollar = scanMoney.nextLine();
        balance.setCurrentBalance(balance.getCurrentBalance() + dollarStringToInt(stringDollar));
    }


    public void purchaseItem() {
        Scanner purchaseScan = new Scanner(System.in);
        // user enters itemCode
        userItemCode = purchaseScan.nextLine().toUpperCase();
        Products products = productsForSale.get(userItemCode);

        if (!(products.getItemStock() == 0)) {
            if (balance.getCurrentBalance() >= products.getPrice()) {
                balance.setCurrentBalance(balance.getCurrentBalance() - products.getPrice());
                products.setItemStock(products.getItemStock() - 1);
            }
        }
    }


    public void finishTransaction() {
        //  in pennies
        //  49 / 25 -> 1    49 % 25 -> 24
        int quarters = balance.getCurrentBalance() / 25;
        balance.setCurrentBalance(balance.getCurrentBalance() % 25);
        int dime = balance.getCurrentBalance() / 10;
        balance.setCurrentBalance(balance.getCurrentBalance() % 10);
        int nickel = balance.getCurrentBalance() / 5;
        balance.setCurrentBalance(balance.getCurrentBalance() % 5);
        int penny = balance.getCurrentBalance();
        balance.setCurrentBalance(0);
    }

    public String dollarIntToString(int dollarInInteger) {
        int dollar = dollarInInteger / 100;
        int penny = dollarInInteger % 100;  //1650 / 100 ->50 "0"
        return penny == 0 ? dollar + ".00" : dollar + "." + penny;
    }

    public Integer dollarStringToInt(String dollarInString) {
        if (dollarInString.contains("\\.")) {
            String[] temp = dollarInString.split("\\.");
            return Integer.parseInt(temp[0]) * 100 + Integer.parseInt(temp[1]);
        } else {
            return Integer.parseInt(dollarInString) * 100;
        }
    }

    public int currentBalanceAsStr() {
        return balance.getCurrentBalance();
    }

    public List<String> getItemCodeList() {
        return itemCodeList;
    }

    public Map<String, Products> getProductsForSale() {
        return productsForSale;
    }

    public String getUserItemCode() {
        return userItemCode;
    }
}
