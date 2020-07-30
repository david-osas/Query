package com.example.query.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SearchDAO {
    @Query("SELECT * FROM searches ORDER BY uid DESC")
    List<Searches> getAll();

    @Query("SELECT * FROM searches WHERE searchItem LIKE :search LIMIT 1")
    Searches findSearch(String search);

    @Query("SELECT * FROM searches WHERE searchItem LIKE :search LIMIT 1")
    boolean isSearchPresent(String search);

    @Query("SELECT COUNT(searchItem) FROM searches")
    int getSize();

    @Query("SELECT * FROM searches LIMIT 1")
    Searches getLast();

    @Insert
    void insert(Searches item);

    @Delete
    void delete(Searches items);
}
