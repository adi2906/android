package com.example.proiect.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proiect.database.model.Item;
import com.example.proiect.database.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from users")
    List<User> getAll();

    @Insert
    long insert(User user);

    @Update
    int update(User user);

    @Delete
    int delete(User user);
}
