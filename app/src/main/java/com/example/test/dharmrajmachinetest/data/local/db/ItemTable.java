package com.example.test.dharmrajmachinetest.data.local.db;

/**
 * Created by Test on 6/1/2018.
 */

public class ItemTable {

    public static final String TABLE_NAME = "items";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_Name = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CategoryId = "category_id";
    public static final String COLUMN_Quantity = "quantity";
    public static final String COLUMN_is_imported = "is_imported";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String note;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_Name + " TEXT,"
                    + COLUMN_PRICE + " REAL,"
                    + COLUMN_CategoryId + " INTEGER,"
                    + COLUMN_Quantity + " INTEGER,"
                    + COLUMN_is_imported + " INTEGER,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public ItemTable() {
    }

    public ItemTable(int id, String note, String timestamp) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
