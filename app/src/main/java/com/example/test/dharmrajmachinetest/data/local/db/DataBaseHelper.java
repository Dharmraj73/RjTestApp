package com.example.test.dharmrajmachinetest.data.local.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.test.dharmrajmachinetest.data.roomdb.model.Category;
import com.example.test.dharmrajmachinetest.data.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Test on 6/1/2018.
 */

public class DataBaseHelper  extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(CategoryTable.CREATE_TABLE);
        db.execSQL(ItemTable.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + CategoryTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemTable.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }



    public long insertCategory(Category category) {
        long id = 0;
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            //cv.put(CategoryTable.COLUMN_ID, category.id);
            cv.put(CategoryTable.COLUMN_NOTE, category.name);
            id = db.insert(CategoryTable.TABLE_NAME, null, cv);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }



    public List<Category> getAllCategoryT() {
        List<Category> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CategoryTable.TABLE_NAME + " ORDER BY " +
                ItemTable.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category item = new Category();
                item.id = cursor.getInt(cursor.getColumnIndex(CategoryTable.COLUMN_ID));
                item.name = cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_NOTE));
                // note.setTimestamp(cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_TIMESTAMP));
                notes.add(item);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }


    public long insertItem(Item item) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        long id= 0;

        try {
            //cv.put(ItemTable.COLUMN_ID, item.id);
            cv.put(ItemTable.COLUMN_CategoryId, item.category_id);
            cv.put(ItemTable.COLUMN_Name, item.name);
            cv.put(ItemTable.COLUMN_PRICE, item.price);
            cv.put(ItemTable.COLUMN_Quantity, item.quantity);
            cv.put(ItemTable.COLUMN_is_imported, item.isImported ?1:0);
             id = db.insert(ItemTable.TABLE_NAME, null, cv);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }


    public List<Item> getAllNotes() {
        List<Item> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ItemTable.TABLE_NAME + " ORDER BY " +
                ItemTable.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.id = cursor.getInt(cursor.getColumnIndex(ItemTable.COLUMN_ID));
                item.name = cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_Name));
                item.category_id = cursor.getInt(cursor.getColumnIndex(ItemTable.COLUMN_CategoryId));
                item.price = Double.parseDouble(cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_PRICE)));
                item.quantity = cursor.getInt(cursor.getColumnIndex(ItemTable.COLUMN_Quantity));
                item.isImported = cursor.getInt(cursor.getColumnIndex(ItemTable.COLUMN_is_imported))==1;
               // note.setTimestamp(cursor.getString(cursor.getColumnIndex(ItemTable.COLUMN_TIMESTAMP));
                notes.add(item);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public List<Item> getAllItems() {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        List<Item> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            String Query = "SELECT * FROM " + ItemTable.TABLE_NAME;
            cursor = db.rawQuery(Query, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Item item = new Item();
                    item.id = cursor.getInt(cursor.getColumnIndex("id"));
                    item.category_id = cursor.getInt(cursor.getColumnIndex("category_id"));
                    item.name = cursor.getString(cursor.getColumnIndex("name"));
                    item.price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
                    item.quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                    item.isImported = cursor.getInt(cursor.getColumnIndex("imported")) == 1;
                    list.add(item);
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
