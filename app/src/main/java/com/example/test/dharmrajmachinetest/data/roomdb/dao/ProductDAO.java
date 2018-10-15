package com.example.test.dharmrajmachinetest.data.roomdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.test.dharmrajmachinetest.data.roomdb.model.Product;
import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    long insert(Product product);

    @Update
    int update(Product product);

    @Query("DELETE FROM product_table")
    void deleteAll();

    @Query("SELECT * from product_table")
    List<Product> getAllProducts();

}
