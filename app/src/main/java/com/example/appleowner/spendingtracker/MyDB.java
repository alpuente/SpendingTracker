package com.example.appleowner.spendingtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by appleowner on 1/15/16.
 */
public class MyDB extends SQLiteOpenHelper {
    final static int DB_VERSION = 1;
    final static String DB_NAME = "mydb.s3db";
    SQLiteDatabase db;
    Context context;

    public MyDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // Store the context for later use
        this.context = context;
    }

    public SQLiteDatabase makeDB() {
        try {
            db = context.openOrCreateDatabase("budget.db", context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "groceries"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "gas"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "alcohol"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "restaurants"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "miscellaneous"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");
            for (int i = 0; i < 15; i++) {
                System.out.println("DB");
            }

        } catch (Exception e) {
            for (int i = 0; i < 15; i++) {
                System.out.println("DB FAILURE");
            }
        }
            return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < 15; i++) {
            System.out.println("DB");
        }
        try {
            db = context.openOrCreateDatabase("budget.db", context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "groceries"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "gas"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "alcohol"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "restaurants"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "miscellaneous"
                    + " (_id INTEGER PRIMARY KEY, price INTEGER, date TEXT, description TEXT);");
            for (int i = 0; i < 15; i++) {
                System.out.println("DB");
            }

        } catch (Exception e) {
            for (int i = 0; i < 15; i++) {
                System.out.println("DB FAILURE");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
