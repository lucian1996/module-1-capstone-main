package com.techelevator;

public class Products {

    private String itemCode;
    private String productName;
    private int price;
    private int itemStock;


    public Products(String itemCode, String productName, int price, int itemStock) {
        this.itemCode = itemCode;
        this.productName = productName;
        this.price = price;
        this.itemStock = itemStock;
    }

    public Products() {

    }
    public String getItemCode() {
        return itemCode;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    public String getProductName() {
        return productName;
    }
    public int getPrice() {
        return price;
    }
    public int getItemStock() {
        return itemStock;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setItemStock(int itemStock) {
        this.itemStock = itemStock;
    }
}
