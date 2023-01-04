package com.techelevator;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachine {
    private final int currentMoneyProvided = 0;
    private final Balance balance = new Balance();
    private final List<String> itemCodeList = new ArrayList<>();
    private final Map<String, Product> productList = new HashMap<>();
    private final List<Integer> changes = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private String userItemCode;
    private boolean isValidCode;
    private boolean isPurchasable;
    private String stringDollar;


    //get data from VendingMachine.csv
    public void getData(Scanner fileScanner) {
            while (fileScanner.hasNextLine()) {
                String inputData = fileScanner.nextLine();
                String[] itemData = inputData.split("\\|");
                String itemCode = itemData[0];
                String productName = itemData[1];
                String price = itemData[2];
                String category = itemData[3];
                int stock = 5;

                int priceInPenny = (int) (Double.parseDouble(price) * 100);
                itemCodeList.add(itemCode);

                // Creating a Map<item code, Products class>
                //                 item code              category  name      price       # in stock
                productList.put(itemCode, new Product(category, productName, priceInPenny, stock));
            }
        balance.setCurrentBalance(0);
    }

    public void takeMoney() {
        stringDollar = getUserInput();
        if (dollarStringToInt(stringDollar) < 0){
            System.out.println("Cannot Deposit negative amount");
        } else if (dollarStringToInt(stringDollar) > 500000) {
            System.out.println("Cannot Deposit more than $5000");
        } else {
            balance.setCurrentBalance(balance.getCurrentBalance() + dollarStringToInt(stringDollar));
        }
    }

    public void purchaseItem() {
            userItemCode = getUserInput().toUpperCase();
            Product itemChoice = productList.get(userItemCode);
            if (productList.get(userItemCode) == null) {
                System.out.println("Invalid Item Code");
                isValidCode = false;
            } else {
                isValidCode = true;
                if (!(itemChoice.getItemStock() == 0)) {
                    if (balance.getCurrentBalance() >= itemChoice.getPrice()) {
                        balance.setCurrentBalance(balance.getCurrentBalance() - itemChoice.getPrice());
                        itemChoice.setItemStock(itemChoice.getItemStock() - 1);
                        isPurchasable = true;
                    } else {
                        isPurchasable = false;
                    }
                } else {
                    isPurchasable = false;
                }
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
        try (PrintWriter logOutput = new PrintWriter(new FileWriter("Log.txt", true))) {
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            //date     time    AM/PM    Feed money/item+item code/Give change    price/transaction     total balance
            String date = String.valueOf(LocalDate.now());
            String time = String.valueOf(localTime.format(dateTimeFormatter));
            if (action.equals("FEED MONEY")) {
                logOutput.append(String.format("%s %s %s $%s $%s%n", date, time, action, dollarIntToString(dollarStringToInt(stringDollar)), dollarIntToString(balance.getCurrentBalance())));
            } else if (action.equals("ITEM CODE")){
                logOutput.append(String.format("%s %s %s %s $%s $%s%n", date, time, productList.get(userItemCode).getProductName(), userItemCode, dollarIntToString(productList.get(userItemCode).getPrice()), dollarIntToString(balance.getCurrentBalance())));
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
        if (dollarInString.contains(".")) {
            String[] temp = dollarInString.split("\\.");
            return Integer.parseInt(temp[0]) * 100 + Integer.parseInt(temp[1]);
        } else {
            return Integer.parseInt(dollarInString) * 100;
        }
    }

    public int currentBalanceAsStr() {
        return balance.getCurrentBalance();
    }
    public String getUserInput () {
        return scanner.nextLine();
    }
    public List<String> getItemCodeList() {
        return itemCodeList;
    }
    public Map<String, Product> getProductsForSale() {
        return productList;
    }
    public String getUserItemCode() {
        return userItemCode;
    }
    public boolean isPurchasable() {
        return isPurchasable;
    }
    public boolean isValidCode() {
        return isValidCode;
    }
    public void resetIsValidCode() {
        this.isValidCode = false;
    }
}
