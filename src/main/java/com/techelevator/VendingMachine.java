package com.techelevator;

import java.io.*;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachine {
    private List<String> itemCodeList = new ArrayList<>();
    private int currentMoneyProvided = 0;
    private Balance balance = new Balance();
    private Map<String, Products> productsForSale = new HashMap<>();
    private String userItemCode;
    private List<Integer> changes = new ArrayList<>();
    private boolean isPurchasable;
    private String stringDollar;


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
        stringDollar = scanMoney.nextLine();
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
                isPurchasable = true;
            } else {
                isPurchasable = false;
            }
        } else {
            isPurchasable = false;
        }

    }

    public List<Integer> finishTransaction() {
        balance.balanceToChange();
        changes.add(balance.getQuarter());
        changes.add(balance.getDime());
        changes.add(balance.getNickel());
        changes.add(balance.getPenny());
        return changes;
    }


    public void logger(String action) {
        String path = "Log.txt";
        File logFile = new File(path);

        try (PrintWriter logOutput = new PrintWriter(new FileWriter("Log.txt", true)))


        {
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            //date     time    AM/PM    Feed money/item+item code/Give change    price/transaction     total balance
            String date = String.valueOf(LocalDate.now());
            String time = String.valueOf(localTime.format(dateTimeFormatter));
            if (action.equals("FEED MONEY")) {
                logOutput.append(String.format("%s %s %s $%s $%s%n", date, time, action, dollarIntToString(dollarStringToInt(stringDollar)), dollarIntToString(balance.getCurrentBalance())));
            } else if (action.equals("ITEM CODE")){
                logOutput.append(String.format("%s %s %s %s $%s $%s%n", date, time, productsForSale.get(userItemCode).getProductName(), userItemCode, dollarIntToString(productsForSale.get(userItemCode).getPrice()), dollarIntToString(balance.getCurrentBalance())));
            } else if (action.equals("GIVE CHANGE")){
                logOutput.append(String.format("%s %s %s $%s $%s%n", date, time, action, dollarIntToString(balance.getCurrentBalance()), dollarIntToString(dollarStringToInt("0"))));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open the file for writing.");
        } catch (IOException e) {
            System.err.println("file not found.");        }
    }





    public String dollarIntToString(int dollarInInteger) {
        int dollar = dollarInInteger / 100;
        int penny = dollarInInteger % 100;  //1650 / 100 ->50 "0"
        String pennyAsStr = "";
        if (penny < 10) {
            pennyAsStr = "0" + penny;
        } else {
            pennyAsStr = String.valueOf(penny);
        }
        return penny == 0 ? dollar + ".00" : dollar + "." + pennyAsStr;
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

    public boolean isPurchasable() {
        return isPurchasable;
    }
}
