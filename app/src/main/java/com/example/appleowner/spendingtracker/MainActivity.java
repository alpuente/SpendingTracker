package com.example.appleowner.spendingtracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MyDB mydb = new MyDB(this);
        db = mydb.makeDB();

        Context context = this;

        TextView textView = (TextView) findViewById(R.id.spending); // get the text view
        textView.setText(getSpending());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddPurchaseActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        TextView textView = (TextView) findViewById(R.id.spending); // get the text view
        textView.setText(getSpending());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            Cursor curs = db.rawQuery("select * from " + items[i] + ";", null);
            curs.moveToFirst();
            curs.moveToNext();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
