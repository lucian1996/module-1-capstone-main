package com.techelevator;

public class Products {
    private String category;
    private String productName;
    private int price;
    private int itemStock;


    public Products(String category, String productName, int price, int itemStock) {
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.itemStock = itemStock;
    }

    public String getCategory() {
        return category;
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
    public void setCategory(String category) {
        this.category = category;
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
