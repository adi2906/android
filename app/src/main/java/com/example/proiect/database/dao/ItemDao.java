package com.example.proiect.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proiect.database.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("select * from items")
    List<Item> getAll();

//    @Query("select * from items WHERE userId = userId")
//    List<Item> findItemsForUser(final int userId);

    @Insert
    long insert(Item item);

    @Update
    int update(Item item);

    @Delete
    int delete(Item item);
}
