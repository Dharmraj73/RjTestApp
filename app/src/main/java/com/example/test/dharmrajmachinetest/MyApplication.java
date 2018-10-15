package com.example.test.dharmrajmachinetest;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.test.dharmrajmachinetest.data.roomdb.LocalDatabase;

public class MyApplication extends Application {

    private static volatile LocalDatabase INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        // create Database object.
        getDatabase(getApplicationContext());
    }


    public static LocalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, "local_database")
                            .addMigrations(LocalDatabase.MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
