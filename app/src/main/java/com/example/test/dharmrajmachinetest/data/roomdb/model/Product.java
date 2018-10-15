package com.example.test.dharmrajmachinetest.data.roomdb.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.test.dharmrajmachinetest.data.roomdb.model.Category;
import com.example.test.dharmrajmachinetest.utility.Constants;
import com.example.test.dharmrajmachinetest.utility.DateConverter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "product_table" ,
        foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id",onDelete = CASCADE))
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public int productId;

    @ColumnInfo(name = "category_id")
    public int categoryId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "imported")
    public boolean isImported;

    @ColumnInfo(name = "manufacture_date")
    @TypeConverters({DateConverter.class})
    public Date manufactureDate;

    @ColumnInfo(name = "expiry_date")
    @TypeConverters({DateConverter.class})
    public Date expiryDate;

    @Ignore
    public boolean isExpiery;



    public String getManufactureDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        return sdf.format(manufactureDate);
    }

    public String getExprieyDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        return sdf.format(expiryDate);
    }

    public boolean checkExpiery(){
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        String expDate = sdf.format(expiryDate);

        Calendar calendar = Calendar.getInstance();
        isExpiery = calendar.getTime().after(expiryDate);
        return isExpiery;
    }

    public double getTotal(){
        return price*quantity;
    }

    public double getSalesTax(){
        double res = (price / 100.0d) * 10;
        return res*quantity;
    }

    public double getImportedTax(){
        if(isImported){
            return quantity * ((price / 100.0f) * 5);
        }
        return 0.0d;
    }

}
