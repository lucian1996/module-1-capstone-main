package com.techelevator;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachine {
    private final Balance userBalance = new Balance();
    private final List<String> itemCodeList = new ArrayList<>();
    private final Map<String, Product> productList = new HashMap<>();
    private final List<Integer> returnChange = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private String userItemCode;
    private boolean isValidCode;
    private boolean isPurchasable;
    private String stringDollar;
    private Product itemChoice;


    //get data from VendingMachine.csv
    public void getData(Scanner fileScanner) {
            while (fileScanner.hasNextLine()) {
                String input = fileScanner.nextLine();
                String[] itemData = input.split("\\|");

                String itemCode = itemData[0];
                String productName = itemData[1];
                String price = itemData[2];
                String category = itemData[3];
                int stock = 5;

                int priceInPenny = (int) (Double.parseDouble(price) * 100);
                itemCodeList.add(itemCode);
                productList.put(itemCode, new Product(category, productName, priceInPenny, stock));
            }
        userBalance.setBalance(0);
    }

    public void takeMoney() {
        stringDollar = userInput();
        if (dollarStringToInt(stringDollar) < 0){
            System.out.println("Cannot Deposit negative amount");
        } else if (dollarStringToInt(stringDollar) > 500000) {
            System.out.println("Cannot Deposit more than $5000");
        } else {
            userBalance.setBalance(userBalance.getBalance() + dollarStringToInt(stringDollar));
        }
    }

    public void purchaseItem() {
            userItemCode = userInput().toUpperCase();
            itemChoice = productList.get(userItemCode);
            if (productList.get(userItemCode) == null) {
                System.out.println("Invalid Item Code");
                isValidCode = false;
            } else {
                isValidCode = true;
                if (itemChoice.getItemStock() <= 1) {
                    if (userBalance.getBalance() >= itemChoice.getPrice()) {
                        userBalance.setBalance(userBalance.getBalance() - itemChoice.getPrice());
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
        userBalance.balanceToChange();
        returnChange.add(userBalance.getQuarter());
        returnChange.add(userBalance.getDime());
        returnChange.add(userBalance.getNickel());
        returnChange.add(userBalance.getPenny());
        return returnChange;
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
            switch (action) {
                case "FEED MONEY":
                    logOutput.append
                    (String.format("%s %s %s $%s $%s%n",
                                    date, time, action, dollarIntToString(dollarStringToInt(stringDollar)),
                                    dollarIntToString(userBalance.getBalance())));
                    break;
                case "ITEM CODE":
                    logOutput.append
                    (String.format("%s %s %s %s $%s $%s%n",
                                    date, time, productList.get(userItemCode).getProductName(), userItemCode,
                                    dollarIntToString(productList.get(userItemCode).getPrice()), dollarIntToString(userBalance.getBalance())));
                    break;
                case "GIVE CHANGE":
                    logOutput.append
                    (String.format("%s %s %s $%s $%s%n",
                                    date, time, action, dollarIntToString(userBalance.getBalance()),
                                    dollarIntToString(dollarStringToInt("0"))));
                    break;
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
            String[] dollarInCents = dollarInString.split("\\.");
            return Integer.parseInt(dollarInCents[0]) * 100 +
                   Integer.parseInt(dollarInCents[1]);
        } else {
            return Integer.parseInt(dollarInString) * 100;
        }
    }

    public int getItemChoicePrice() {
        return itemChoice.getPrice();
    }
    public int currentBalanceAsStr() {
        return userBalance.getBalance();
    }
    public String userInput () {
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
