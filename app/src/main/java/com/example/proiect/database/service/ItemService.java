package com.example.proiect.database.service;

import android.content.Context;

import com.example.proiect.asyncTask.network.AsyncTaskRunner;
import com.example.proiect.asyncTask.network.Callback;
import com.example.proiect.database.DatabaseManager;
import com.example.proiect.database.dao.ItemDao;
import com.example.proiect.database.model.Item;

import java.util.List;
import java.util.concurrent.Callable;

public class ItemService {

    private final ItemDao itemDao;
    private final AsyncTaskRunner taskRunner;

    public ItemService(Context context) {
        itemDao = DatabaseManager.getInstance(context).getItemDao();
        taskRunner = new AsyncTaskRunner();
    }

    public List<Item> getAllV2() {
        return itemDao.getAll();
    }

    public void getAll(Callback<List<Item>> callback) {
        Callable<List<Item>> callable = new Callable<List<Item>>() {
            @Override
            public List<Item> call() {
                return itemDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Item> callback, final Item item) {
        Callable<Item> callable = new Callable<Item>() {
            @Override
            public Item call() {
                if (item == null) {
                    return null;
                }
                long id = itemDao.insert(item);
                if (id == -1) {
                    return null;
                }
                item.setId(id);
                return item;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<Item> callback, final Item item) {
        Callable<Item> callable = new Callable<Item>() {
            @Override
            public Item call() {
                if (item == null) {
                    return null;
                }
                int count = itemDao.update(item);
                if (count < 1) {
                    return null;
                }
                return item;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Item item) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (item == null) {
                    return -1;
                }
                return itemDao.delete(item);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
