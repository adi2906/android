package com.example.proiect.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "items")

public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_userId")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "price")
    private int price;
    @ColumnInfo(name = "weight")
    private int weight;
    @ColumnInfo(name = "userId")
    public int userId;

    public Item( long id, String name, int price, int weight, int userId) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.id = id;
        this.userId = userId;
    }

    @Ignore
    public Item(String name, int price, int weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
