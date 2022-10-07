package com.techelevator;

public class Balance {

    private int currentBalance;
    private int quarter;
    private int dime;
    private int nickel;
    private int penny;
    private int remainingBalance;




    public void balanceToChange() {
        quarter = currentBalance / 25;
        currentBalance = currentBalance % 25;
        dime = currentBalance / 10;
        currentBalance = currentBalance % 10;
        nickel = currentBalance / 5;
        currentBalance = currentBalance % 5;
        penny = currentBalance;

    }

    public int getQuarter() {
        return quarter;
    }

    public int getDime() {
        return dime;
    }

    public int getNickel() {
        return nickel;
    }

    public int getPenny() {
        return penny;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }



}
