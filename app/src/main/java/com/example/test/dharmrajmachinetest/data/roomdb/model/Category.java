package com.example.test.dharmrajmachinetest.data.roomdb.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Test on 6/1/2018.
 */

@Entity(tableName = "category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    public Category(){
    }


    public Category(@NonNull String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
