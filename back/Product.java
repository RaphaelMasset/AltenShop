package com.example.altenshop.model;

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
    private String inventoryStatus; // "INSTOCK", "LOWSTOCK", "OUTOFSTOCK"
    private int rating;
    private long createdAt;
    private long updatedAt;

    // Constructeurs, getters, setters (use Lombok @Data pour simplifier)
}
