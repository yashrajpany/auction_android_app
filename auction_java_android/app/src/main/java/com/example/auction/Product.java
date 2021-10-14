package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Product {

    private  int id;
    private String title, description, bidName;
    private double price;

    public Product(int id, String title, String description, double price,String bidName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bidName = bidName;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getBidName() {
        return bidName;
    }
}