package com.example.proiect.database.service;

import android.content.Context;

import com.example.proiect.asyncTask.network.AsyncTaskRunner;
import com.example.proiect.asyncTask.network.Callback;
import com.example.proiect.database.DatabaseManager;
import com.example.proiect.database.dao.UserDao;
import com.example.proiect.database.model.User;

import java.util.List;
import java.util.concurrent.Callable;


public class UserService {

    private final UserDao userDao;
    private final AsyncTaskRunner taskRunner;

    public UserService(Context context) {
        userDao = DatabaseManager.getInstance(context).getUserDao();
        taskRunner = new AsyncTaskRunner();
    }

    public List<User> getAllV2() {
        return userDao.getAll();
    }

    public void getAll(Callback<List<User>> callback) {
        Callable<List<User>> callable = new Callable<List<User>>() {
            @Override
            public List<User> call() {
                return userDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<User> callback, final User user) {
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() {
                if (user == null) {
                    return null;
                }
                long id = userDao.insert(user);
                if (id == -1) {
                    return null;
                }
                user.setId(id);
                return user;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<User> callback, final User user) {
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() {
                if (user == null) {
                    return null;
                }
                int count = userDao.update(user);
                if (count < 1) {
                    return null;
                }
                return user;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final User user) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (user == null) {
                    return -1;
                }
                return userDao.delete(user);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
