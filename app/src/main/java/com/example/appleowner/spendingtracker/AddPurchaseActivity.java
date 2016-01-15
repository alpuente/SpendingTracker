package com.example.appleowner.spendingtracker;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

public class AddPurchaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpinner();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                finish();
            }
        });
    }

    /*
    get the entered values, in addition to the current date
     */
    protected void save() {
        Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        String type = dropdown.getSelectedItem().toString();
        final EditText priceField = (EditText) findViewById(R.id.editText);
        String price = priceField.getText().toString();
        final EditText descriptionFiled = (EditText) findViewById(R.id.editText2);
        String description = descriptionFiled.getText().toString();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String date = month + " " + year + " " + day;

        MainActivity.db.execSQL("insert into " + type + " (price, description, date) values " + "(" + price + ", '" + description + "', '" + date + "');");
    }

    protected void setSpinner() {
        Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        ContentResolver cr;
        Cursor c = MainActivity.db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        c.moveToFirst();
        String[] items = new String[c.getCount()-1];
        c.moveToNext(); // skip android_metadata table"insert
        int i = 0;
        while (c.isAfterLast() == false) {
            System.out.println(c.getString(c.getColumnIndex("name")));
            items[i] = c.getString(c.getColumnIndex("name"));
            c.moveToNext();
            i++;
        }
        c.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

}
