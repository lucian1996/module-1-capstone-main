package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String PURCHASING_MENU_FEED_MONEY = "Feed Money";
    private static final String PURCHASING_MENU_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASING_MENU_FINALISE_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASING_MENU_OPTIONS = {PURCHASING_MENU_FEED_MONEY, PURCHASING_MENU_SELECT_PRODUCT, PURCHASING_MENU_FINALISE_TRANSACTION};
    private VendingMachine vendingMachine = new VendingMachine();
    private Menu menu;


    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {
        File inputFile = new File("VendingMachine.csv");
        try (Scanner fileScanner = new Scanner(inputFile)) {
            vendingMachine.getData(fileScanner);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the file.");
        }

        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                vendingMachine.getItemCodeList();
                printStock(vendingMachine);
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                while (true) {
                    System.out.printf("%nCurrent Money Provided: $ %s%n", vendingMachine.dollarIntToString(vendingMachine.currentBalanceAsStr()));
                    String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASING_MENU_OPTIONS);
                    if (purchaseChoice.equals(PURCHASING_MENU_FEED_MONEY)) {
                        System.out.print("Enter dollar bills please: ");
                        vendingMachine.takeMoney();
                        vendingMachine.logger("FEED MONEY");
                        //call log method
                    } else if (purchaseChoice.equals(PURCHASING_MENU_SELECT_PRODUCT)) {
                        printStock(vendingMachine);
                        System.out.print("Enter Item Code: ");
                        vendingMachine.purchaseItem();
                        vendingMachine.logger("ITEM CODE");
                        //call log method
                        System.out.println();
                        String printMessage = "";
                        if (vendingMachine.isPurchasable()){
                            if (vendingMachine.getProductsForSale().get(vendingMachine.getUserItemCode()).getCategory().equals("Chip")) {
                                System.out.println("Crunch Crunch, Yum!");
                            } else if (vendingMachine.getProductsForSale().get(vendingMachine.getUserItemCode()).getCategory().equals("Candy")) {
                                System.out.println("Munch Munch, Yum!");
                            } else if (vendingMachine.getProductsForSale().get(vendingMachine.getUserItemCode()).getCategory().equals("Drink")) {
                                System.out.println("Glug Glug, Yum!");
                            } else if (vendingMachine.getProductsForSale().get(vendingMachine.getUserItemCode()).getCategory().equals("Gum")) {
                                System.out.println("Chew Chew, Yum!");
                            }
                        } else {
                            System.out.println("Insufficient Fund");
                        }
                    } else if (purchaseChoice.equals(PURCHASING_MENU_FINALISE_TRANSACTION)) {
                        vendingMachine.logger("GIVE CHANGE");
                        //call log method
                        for (int i = 0; i < vendingMachine.finishTransaction().get(0); i++) {
                            System.out.println("QUARTER!");
                        }
                        for (int i = 0; i < vendingMachine.finishTransaction().get(1); i++) {
                            System.out.println("DIME!");
                        }
                        for (int i = 0; i < vendingMachine.finishTransaction().get(2); i++) {
                            System.out.println("NICKEL!");
                        }
                        for (int i = 0; i < vendingMachine.finishTransaction().get(3); i++) {
                            System.out.println("PENNY!");
                        }
                        break;
                    }
                }
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                // exit method
            }
        }
    }

    public static void printStock(VendingMachine vendingMachine) {
        for (int i = 0; i < vendingMachine.getItemCodeList().size(); i++) {
            // A1 | product name | price || "SOLD OUT" if out of stock.
            String key = vendingMachine.getItemCodeList().get(i);
            Products products = vendingMachine.getProductsForSale().get(key);

            if (products.getItemStock() == 0) {
                System.out.println("SOLD OUT");
            } else {
                System.out.printf("%s | %-20s | $ %s%n", key, products.getProductName(), vendingMachine.dollarIntToString(products.getPrice()));
            }
        }
    }
}
