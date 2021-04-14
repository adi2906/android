package com.example.proiect.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.proiect.database.model.Item;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "users" )
public class User implements Serializable {

    @ForeignKey(entity = Item.class, parentColumns = "item_userId", childColumns = "user_id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") //-> fk item
    private long id;
    @SerializedName("firstName")
    @ColumnInfo(name = "firstName")
    private String firstName;
    @SerializedName("lastName")
    @ColumnInfo(name = "lastName")
    private String lastName;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private int password;
    @ColumnInfo(name = "birthday")
    private Date birthday;


    public User(long id, String firstName, String lastName, String username, int password, Date birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthday = birthday;

    }


    @Ignore
    public User(String firstName, String lastName, String username, int password, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }

    @Ignore
    public User(String firstName, String lastName, String username, int password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    @Ignore
    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = Integer.parseInt(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", birthday=" + birthday +
                '}';
    }
}
