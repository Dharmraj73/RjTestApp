package com.example.test.dharmrajmachinetest.data.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.test.dharmrajmachinetest.data.roomdb.model.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    long insert(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("DELETE FROM category_table")
    void deleteAll();

    @Query("SELECT * FROM category_table")
    List<Category> getAllCategory();
}
