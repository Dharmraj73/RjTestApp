package com.example.test.dharmrajmachinetest.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public static final String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final String EditableFragment = "isEditableFragment";
    public static final String PRODUCT = "product";
    public static final String POSITION = "position";


    public static Date getStringToDate(String dtStart ){
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
