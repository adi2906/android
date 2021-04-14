package com.example.proiect.util;

import com.example.proiect.database.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserJasonParser {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String BIRTHDAY = "birthday";

    public static List<User> fromJson(String json) {
        try {
            JSONArray array = new JSONArray(json);
            return readUsers(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    private static List<User> readUsers(JSONArray array) throws JSONException {
        List<User> bankAccounts = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            User bankAccount = readUser(array.getJSONObject(i));
            bankAccounts.add(bankAccount);
        }
        return bankAccounts;
    }

    private static User readUser(JSONObject object) throws JSONException {
        String firstName = object.getString(FIRST_NAME);
        String lastName = object.getString(LAST_NAME);
        String username = object.getString(USERNAME);
        String password = object.getString(PASSWORD);
        return new User(firstName, lastName, username, password);
    }
}
