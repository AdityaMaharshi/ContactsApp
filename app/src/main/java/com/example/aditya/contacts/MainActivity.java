package com.example.aditya.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static DbHelper sqldb;
    private FloatingActionButton fab;
    public static Context context;
    static ArrayList<String> values = new ArrayList<String>();
    public static ArrayAdapter<String> adapter;
    static ListView listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqldb = new DbHelper(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        listItem = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listItem.setAdapter(adapter);
        viewAllContacts();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacts.tf = true;
                Intent i = new Intent(MainActivity.this, Contacts.class);
                startActivity(i);
            }
        });
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts.tf = false;
                Intent i = new Intent(MainActivity.this, Contacts.class);
                startActivity(i);
            }
        });
        listItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sqldb.deleteData(String.valueOf(position));
                values.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    private void viewAllContacts() {
        Cursor res = sqldb.getAllData();
        values.clear();
        if (res.getCount() == 0) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            values.add(String.valueOf(buffer.append(res.getString(1) + "\n" + res.getString(2) + "\n" + res.getString(3))));
            adapter.notifyDataSetChanged();
            listItem.refreshDrawableState();
            Log.i("info: ", res.getString(1) + "\n" + res.getString(2) + "\n" + res.getString(3));
            res.moveToNext();
        }
        for (int i = values.size()-1; i >= 1; i--) {
            values.set(i, values.get(i).substring(values.get(i-1).length()));
        }
        res.close();
    }

    public static void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
