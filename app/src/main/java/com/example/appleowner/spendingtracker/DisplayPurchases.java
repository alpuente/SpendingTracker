package com.example.appleowner.spendingtracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class DisplayPurchases extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_purchases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            String table =(String) bundle.get("tableName");
            TextView text = (TextView) findViewById(R.id.displayPurchases);
            String purchaseInfo = getPurchases(table);
            if (purchaseInfo.contentEquals("")) {
                text.setText("No " + table + " purchases for this month.");
            } else {
                text.setText(purchaseInfo);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public String getPurchases(String table) {
        Cursor curs = MainActivity.db.rawQuery("select * from " + table + ";", null);
        curs.moveToFirst();
        String output = "";
        while (curs.isAfterLast() == false) {
            String date = curs.getString(curs.getColumnIndex("date"));
            String datestuff[] = date.split(" ");
            Calendar cal = Calendar.getInstance();
            int currentMonth = cal.get(Calendar.MONTH);
            if (currentMonth == Integer.parseInt(datestuff[0])) { // if the purchase happened this month
                output += "Item: " + curs.getString(curs.getColumnIndex("description")) + " ";
                output += "Price: $" + curs.getInt(curs.getColumnIndex("price")) + " ";
                output += "Date: " + curs.getString(curs.getColumnIndex("date")) + "\n";
            }
            curs.moveToNext();
        }

        return output;
    }

    public String getSpending() {
        ArrayList<String> values = new ArrayList<String>();
        String output = "";
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

        int total = 0;
        for (i = 0; i < items.length; i++) {
            int interimTotal = 0;
            Cursor curs = MainActivity.db.rawQuery("select * from " + items[i] + ";", null);
            curs.moveToFirst();
            System.out.println("curs length" + curs.getCount());
            while (curs.isAfterLast() == false) {
                System.out.println(curs.toString());
                System.out.println("price: " + curs.getString( curs.getColumnIndex("price")));
                int price = curs.getInt(curs.getColumnIndex("price"));
                System.out.println(price);
                interimTotal += price;
                total += price;
                curs.moveToNext();
            }
            output += items[i] + ": " + interimTotal + "\n";
        }

        output += "Total Spent: " + total;

        return output;
    }

}
