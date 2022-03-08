package com.example.auction.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "category_id")
    private int category_id;
    @ColumnInfo(name = "price")
    private int price;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "user_id")
    private int user_id;
    @ColumnInfo(name = "day_start")
    private String day_start;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "data_add")
    private String data_add;
    @ColumnInfo(name = "status")
    private boolean status;

    public Item(int id, String name, int category_id, int price, String image, int user_id, String day_start, String description, String address, String data_add, boolean status) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
        this.price = price;
        this.image = image;
        this.user_id = user_id;
        this.day_start = day_start;
        this.description = description;
        this.address = address;
        this.data_add = data_add;
        this.status = status;
    }

    @Ignore
    public Item() {
    }



    public int getId() {
        return id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDay_start() {
        return day_start;
    }

    public void setDay_start(String day_start) {
        this.day_start = day_start;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getData_add() {
        return data_add;
    }

    public void setData_add(String data_add) {
        this.data_add = data_add;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
