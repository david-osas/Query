package com.example.query.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Searches {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "searchItem")
    public String searchItem;

    public void setItem(String item){
        searchItem = item;
    }


}
