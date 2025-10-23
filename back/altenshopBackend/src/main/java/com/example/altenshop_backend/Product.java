package com.example.altenshop_backend;

public class Product {
    private long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private long shellId;
    private String inventoryStatus; // "INSTOCK" | "LOWSTOCK" | "OUTOFSTOCK"
    private double rating;
    private long createdAt;
    private long updatedAt;

    public Product() {
    }

    public Product(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pdescription) {
        this.description = pdescription;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int pquantity) {
        this.quantity = pquantity;
    }
        

}
