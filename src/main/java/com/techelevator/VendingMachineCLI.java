package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASING_MENU_FEED_MONEY = "Feed Money";
	private static final String PURCHASING_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASING_MENU_FINALISE_TRANSACTION = "Finalise Transaction";
	private static final String[] PURCHASING_MENU_OPTIONS = { PURCHASING_MENU_FEED_MONEY, PURCHASING_MENU_SELECT_PRODUCT, PURCHASING_MENU_FINALISE_TRANSACTION };



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


		  //===== you nay use/modify the existing Menu class or write your own ======
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASING_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				VendingMachine.getData();
				VendingMachine.printStock();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
					if (purchaseChoice.equals(PURCHASING_MENU_FEED_MONEY)){
						VendingMachine.takeMoney();
					} else if (purchaseChoice.equals(PURCHASING_MENU_SELECT_PRODUCT)){
						VendingMachine.getData();
						VendingMachine.printStock();
						VendingMachine.takeOrder();
					} else if (purchaseChoice.equals(PURCHASING_MENU_FINALISE_TRANSACTION)){
					}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				// exit method
			}
		}

	}

}
