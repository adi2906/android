package com.example.proiect.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.proiect.database.dao.ItemDao;
import com.example.proiect.database.dao.UserDao;
import com.example.proiect.database.model.Item;
import com.example.proiect.database.model.User;
import com.example.proiect.util.DateConverter;



@Database(entities = {Item.class, User.class}, exportSchema = false, version = 1)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DAM_DB = "dam_db";

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, DAM_DB)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract ItemDao getItemDao();
    public abstract UserDao getUserDao();
}
