package com.razbyte.personalassistant;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Log extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        TextView tv = (TextView) findViewById(R.id.log_view);

        String all = "";

        try {
            FileInputStream fis = openFileInput("log_data.txt");
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            while(dis.available() != 0){
                 all = dis.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tv.setText(all);
    }

}