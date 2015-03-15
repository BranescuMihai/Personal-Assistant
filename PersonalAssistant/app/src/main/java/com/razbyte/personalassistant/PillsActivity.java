package com.razbyte.personalassistant;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class PillsActivity extends Activity {

    Pastila_list pl = new Pastila_list();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pills);

        String all = "";

        try {
            FileInputStream fis = openFileInput("pills_data.txt");
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            while(dis.available() != 0){
                all = all.concat(dis.readLine());
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String name;


            StringTokenizer st = new StringTokenizer(all, "$");

            while (st.hasMoreTokens()) {
                Pastila p = new Pastila();
                try {
                    name = st.nextToken();
                    p.set_name(name);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                pl.add(p);
            }



    final ArrayList<String> list_data = pl.toString2();

    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list_data);

    ListView listView = (ListView) findViewById(R.id.PillsList);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                pl.remove(position);
                list_data.remove(position);
                adapter.notifyDataSetChanged();
                Iterator<Pastila> it = pl.iterator();

                String data = "";
                while(it.hasNext()) {
                    data = data.concat(it.next().toString());
                }

                try {
                    FileOutputStream fos = openFileOutput("pills_data.txt", Context.MODE_PRIVATE);
                    fos.write(data.getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

}
