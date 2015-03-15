package com.razbyte.personalassistant;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class SettingsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    // ADD CALL

    public void add_caller_click(View view){
        EditText c1 = (EditText) findViewById(R.id.caller);
        String cc = c1.getText().toString();
        c1.setText("");
        try {
            FileOutputStream fos = openFileOutput("caller_data.txt", Context.MODE_PRIVATE);
            fos.write(cc.getBytes());
            Toast.makeText(this, "Caller Set", Toast.LENGTH_LONG).show();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ADD LOCATION

    public void add_location_click(View view){
        String place = "";
        String distance = "";
        EditText m1 = (EditText) findViewById(R.id.location);
        place = place.concat(m1.getText().toString());
        EditText m2 = (EditText) findViewById(R.id.distance);
        distance = distance.concat(m2.getText().toString());
        if(place.matches("") || distance.matches(""))
            Toast.makeText(this, "Location not valid", Toast.LENGTH_LONG).show();
        else {
            ContentValues values = new ContentValues();
            values.put(MoveTable.COLUMN_PLACE, place);
            values.put(MoveTable.COLUMN_DISTANCE, distance);
            getContentResolver().insert(MoveContentProvider.CONTENT_URI, values);
            Toast.makeText(this, "Location Added", Toast.LENGTH_LONG).show();
            m1.setText("");
            m2.setText("");
        }

    }

    // ADD PANIC TEXT

    public void add_panic_text(View view) {

        EditText p1 = (EditText) findViewById(R.id.panicText);
        String tt = p1.getText().toString();
        p1.setText("");
        try {
            FileOutputStream fos = openFileOutput("panic_data.txt", Context.MODE_PRIVATE);
            fos.write(tt.getBytes());
            Toast.makeText(this, "Text Added", Toast.LENGTH_LONG).show();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ADD PILL

    public void add_pill_click(View view){
        EditText et1 = (EditText)findViewById(R.id.pill_name);

        if(et1.getText().toString().matches(""))
            Toast.makeText(this, "Pill not valid", Toast.LENGTH_LONG).show();
        else {
            String d = et1.getText().toString() + "$";
            et1.setText("");

            try {
                FileOutputStream fos = openFileOutput("pills_data.txt", Context.MODE_APPEND);
                fos.write(d.getBytes());
                Toast.makeText(this, "Pill Added", Toast.LENGTH_LONG).show();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // VIEW LOG

    public void log_click(View view){
        startActivity(new Intent(this, Log.class));
    }

    //DELETE LOG CONTENT

    public void log_del(View view) throws IOException {
        String a = "";
        FileOutputStream fos = openFileOutput("log_data.txt", Context.MODE_PRIVATE);
        fos.write(a.getBytes());
        fos.close();
        Toast.makeText(this, "Log cleared", Toast.LENGTH_LONG).show();
    }
}

