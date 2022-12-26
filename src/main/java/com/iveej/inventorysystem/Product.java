package com.iveej.inventorysystem;

public class Product {
    private int id;
    private String name;
    private String category;
    private String description;
    private int quantity;
    private double orgPrice;
    private double sellPrice;

    Product(int id, String name, String category, String description, int quantity, double orgPrice, double sellPrice) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.orgPrice = orgPrice;
        this.sellPrice = sellPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getOrgPrice() {
        return orgPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

}
