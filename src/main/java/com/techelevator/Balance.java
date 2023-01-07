package com.techelevator;

public class Balance {
    private int currentBalance;
    private int quarterBalance;
    private int dimeBalance;
    private int nickelBalance;
    private int pennyBalance;

    public void balanceToChange() {
        quarterBalance = currentBalance / 25;
        currentBalance = currentBalance % 25;

        dimeBalance = currentBalance / 10;
        currentBalance = currentBalance % 10;

        nickelBalance = currentBalance / 5;
        currentBalance = currentBalance % 5;

        pennyBalance = currentBalance;
    }

    public int getQuarterBalance() {
        return quarterBalance;
    }
    public int getDimeBalance() {
        return dimeBalance;
    }
    public int getNickelBalance() {
        return nickelBalance;
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