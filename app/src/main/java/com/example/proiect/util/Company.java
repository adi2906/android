package com.example.proiect.util;

import java.io.Serializable;

public class Company implements Serializable {

    private String id;
    private String name;
    private String owner;
    private String category;

    public Company() {
    }

    public Company(String id, String name, String owner, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
