package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VendingMachineCLI {
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String PURCHASING_MENU_FEED_MONEY = "Feed Money";
    private static final String PURCHASING_MENU_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASING_MENU_FINALISE_TRANSACTION = "Finish Transaction";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String[] PURCHASING_MENU_OPTIONS = {PURCHASING_MENU_FEED_MONEY, PURCHASING_MENU_SELECT_PRODUCT, PURCHASING_MENU_FINALISE_TRANSACTION};
    private final VendingMachine userVendingMachine = new VendingMachine();
    private final Menu userMenu;

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
        while (true) {
            String choice = (String) userMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                userVendingMachine.getItemCodeList();
                printStock(userVendingMachine);
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                while (true) {
                    System.out.printf("%nCurrent Money Provided: $ %s%n", userVendingMachine.dollarIntToString(userVendingMachine.currentBalanceAsStr()));
                    String purchaseChoice = (String) userMenu.getChoiceFromOptions(PURCHASING_MENU_OPTIONS);
                    if (purchaseChoice.equals(PURCHASING_MENU_FEED_MONEY)) {
                        System.out.print("Enter dollar bills please: ");
                        userVendingMachine.takeMoney();
                        userVendingMachine.logger("FEED MONEY");
                        //call log method
                    } else if (purchaseChoice.equals(PURCHASING_MENU_SELECT_PRODUCT)) {
                        printStock(userVendingMachine);
                        while(!userVendingMachine.isValidCode()) {
                            System.out.print("Enter Item Code: ");
                            userVendingMachine.purchaseItem();
                        }
                        userVendingMachine.resetIsValidCode();
                        userVendingMachine.logger("ITEM CODE");
                        //call log method
                        System.out.println();
                        String printMessage = "";
                        if (userVendingMachine.isPurchasable()) {
                            String curCat = userVendingMachine.getProductsForSale().get(userVendingMachine.getUserItemCode()).getCategory();
                            if (curCat.equals("Chip")) {
                                System.out.println("Crunch Crunch, Yum!");
                            } else if (curCat.equals("Candy")) {
                                System.out.println("Munch Munch, Yum!");
                            } else if (curCat.equals("Drink")) {
                                System.out.println("Glug Glug, Yum!");
                            } else if (curCat.equals("Gum")) {
                                System.out.println("Chew Chew, Yum!");
                            }
                        } else {
                            System.out.println("Insufficient Fund");
                        }
                    } else if (purchaseChoice.equals(PURCHASING_MENU_FINALISE_TRANSACTION)) {
                        userVendingMachine.logger("GIVE CHANGE");
                        //call log method
                        for (int i = 0; i < userVendingMachine.finishTransaction().get(0); i++) {
                            System.out.println("QUARTER!");
                        }
                        for (int i = 0; i < userVendingMachine.finishTransaction().get(1); i++) {
                            System.out.println("DIME!");
                        }
                        for (int i = 0; i < userVendingMachine.finishTransaction().get(2); i++) {
                            System.out.println("NICKEL!");
                        }
                        for (int i = 0; i < userVendingMachine.finishTransaction().get(3); i++) {
                            System.out.println("PENNY!");
                        }
                        break;
                    }
                }
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                break;
            }
        }
    }

    public VendingMachineCLI(Menu menu) {
        this.userMenu = menu;
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