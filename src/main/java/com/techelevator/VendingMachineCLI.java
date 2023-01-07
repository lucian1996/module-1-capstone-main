package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VendingMachineCLI {
    private static final String FEED_MONEY = "Feed Money";
    private static final String PURCHASE = "Purchase Item";
    private static final String FINALISE_TRANSACTION = "Finish Transaction";
    private static final String EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {FEED_MONEY, PURCHASE, FINALISE_TRANSACTION, EXIT};
    private final VendingMachine userVendingMachine = new VendingMachine();
    private final Menu userMenu;
    private String returnPurchase = " ";

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }

    public void run() {
        File inputFile = new File("VendingMachine.csv");
        try (Scanner fileScanner = new Scanner(inputFile)) {
            userVendingMachine.getData(fileScanner);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the input file.");
        }

        label:
        while (true) {
            mainMenuRefresh();
            String choice = (String) userMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            switch (choice) {
                case FEED_MONEY:
                    returnPurchase = " ";
                    System.out.print("\n" + "Enter dollar bills please: ");
                    userVendingMachine.takeMoney();
                    userVendingMachine.logger("FEED MONEY");
                    break;
                case PURCHASE:
                    while (!userVendingMachine.isValidCode()) {
                        System.out.print("\n" + "Enter Item Code please: ");
                        userVendingMachine.purchaseItem();
                    }
                    userVendingMachine.resetIsValidCode();
                    userVendingMachine.logger("ITEM CODE");
                    if (userVendingMachine.isPurchasable()) {
                        String selectedCategory = userVendingMachine.getProductsForSale().get(userVendingMachine.getUserItemCode()).getCategory();
                        switch (selectedCategory) {
                            case "Chip":
                                returnPurchase = ("Crunch Crunch, Yum!");
                                break;
                            case "Candy":
                                returnPurchase = ("Munch Munch, Yum!");
                                break;
                            case "Drink":
                                returnPurchase = ("Glug Glug, Yum!");
                                break;
                            case "Gum":
                                returnPurchase = ("Chew Chew, Yum!");
                                break;
                        }
                    } else if (userVendingMachine.currentBalanceAsStr() < userVendingMachine.getItemChoicePrice()){
                        returnPurchase = ("Insufficient Fund");
                    }
                    break;
                case FINALISE_TRANSACTION:
                    returnPurchase = " ";
                    userVendingMachine.logger("GIVE CHANGE");
                    for (int i = 0; i < userVendingMachine.getChange().get(0); i++) {
                        System.out.println("QUARTER!");
                    }
                    for (int i = 0; i < userVendingMachine.getChange().get(1); i++) {
                        System.out.println("DIME!");
                    }
                    for (int i = 0; i < userVendingMachine.getChange().get(2); i++) {
                        System.out.println("NICKEL!");
                    }
                    for (int i = 0; i < userVendingMachine.getChange().get(3); i++) {
                        System.out.println("PENNY!");
                    }
                    break;
                case EXIT:
                    break label;
            }
        }
    }

    public VendingMachineCLI(Menu menu) {
        this.userMenu = menu;
    }

    public void mainMenuRefresh() {
        System.out.println("\n" + "Welcome to Luke's Vending Machine!" + "\n");
        printStock(userVendingMachine);
        System.out.printf("%nCurrent Money Provided: $ %s%n", userVendingMachine.dollarIntToString(userVendingMachine.currentBalanceAsStr()));
        System.out.println(returnPurchase);
    }

    public static void printStock(VendingMachine vendingMachine) {
        for (int i = 0; i < vendingMachine.getItemCodeList().size(); i++) {
            // A1 | product name | price || "SOLD OUT" if out of stock.
            String key = vendingMachine.getItemCodeList().get(i);
            Product products = vendingMachine.getProductsForSale().get(key);
            if (products.getItemStock() == 0) {
                System.out.println("SOLD OUT");
            } else {
                System.out.printf("%s | %-20s | $ %s%n", key, products.getProductName(), vendingMachine.dollarIntToString(products.getPrice()));
            }
        }
    }
}