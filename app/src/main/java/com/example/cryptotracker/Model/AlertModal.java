package com.example.cryptotracker.Model;

public class AlertModal {
    private String name;
    private String price;
    private double limit;

    public AlertModal(String name, String price, double limit) {
        this.name = name;
        this.price = price;
        this.limit = limit;
    }
}
