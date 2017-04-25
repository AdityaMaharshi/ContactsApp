package com.example.aditya.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.aditya.contacts.DbHelper;

/**
 * Created by Maharshi Aditya Pothani on 4/21/17.
 */

public class Contacts extends AppCompatActivity {
    static Button button;
    static TextView title;
    EditText fullname, pnumber, email;
    public static int id;
    DbHelper sqldb;
    public static boolean tf = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        sqldb = new DbHelper(this);
        button = (Button) findViewById(R.id.button);
        title = (TextView) findViewById(R.id.title);
        fullname = (EditText) findViewById(R.id.fullname);
        pnumber = (EditText) findViewById(R.id.pnumber);
        email = (EditText) findViewById(R.id.email);
        if (tf) {
            title.setText("Add New Contact");
            button.setText("ADD");
            AddNewContact();
        }

        else if (!tf){
            title.setText("Update chosen contact");
            button.setText("UPDATE");
            UpdateExistingContact();
        }
    }

    public void AddNewContact() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = sqldb.insertData(fullname.getText().toString(), pnumber.getText().toString(), email.getText().toString());
                if (isInserted==true) {
                    Toast.makeText(Contacts.this, "New contact details successfully added!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Contacts.this, "An error has occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void UpdateExistingContact() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = sqldb.updateData(String.valueOf(id), fullname.getText().toString(),
                        pnumber.getText().toString(), email.getText().toString());
                MainActivity.values.set(id, fullname.getText() + "\n" + pnumber.getText() + "\n" + email.getText());
                if (isUpdate == true) {
                    Toast.makeText(Contacts.this, "Data inserted!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Contacts.this, "There was an error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
