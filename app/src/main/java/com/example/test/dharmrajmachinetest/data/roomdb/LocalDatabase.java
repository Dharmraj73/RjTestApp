package com.example.test.dharmrajmachinetest.data.roomdb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.example.test.dharmrajmachinetest.data.roomdb.model.Category;
import com.example.test.dharmrajmachinetest.data.roomdb.model.Product;
import com.example.test.dharmrajmachinetest.utility.DateConverter;
import com.example.test.dharmrajmachinetest.data.roomdb.dao.CategoryDAO;
import com.example.test.dharmrajmachinetest.data.roomdb.dao.ProductDAO;
import com.example.test.dharmrajmachinetest.utility.TimestampConverter;


@Database( entities = {Category.class, Product.class}, version = 1)
@TypeConverters({DateConverter.class, TimestampConverter.class})
public abstract class LocalDatabase extends RoomDatabase {

    public abstract CategoryDAO categoryDAO();

    public abstract ProductDAO productDAO();


    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //database.execSQL("ALTER TABLE product " + " ADD COLUMN price INTEGER");
            // enable flag to force update products
        }
    };


}
