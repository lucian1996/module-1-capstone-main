package com.techelevator;

public class Balance {
    private int currentBalance;
    private int quarterBalance;
    private int dimeBalance;
    private int nickleBalance;
    private int pennyBalance;
    private int quarter;
    private int dime;
    private int nickel;

    public void balanceToChange() {
        quarter = currentBalance / 25;
        quarterBalance = currentBalance % 25;

        dime = currentBalance / 10;
        dimeBalance = currentBalance - quarterBalance % 10;

        nickel = currentBalance / 5;
        nickleBalance = currentBalance - quarterBalance - dimeBalance % 5;

        pennyBalance = currentBalance - quarterBalance - dimeBalance - nickleBalance;
    }

    public int getQuarterBalance() {
        return quarter;
    }
    public int getDimeBalance() {
        return dime;
    }
    public int getNickelBalance() {
        return nickel;
    }
    public int getPennyBalance() {
        return pennyBalance;
    }
    public int getBalance() {
        return currentBalance;
    }
    public void setBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }
}