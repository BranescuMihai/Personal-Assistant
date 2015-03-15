package com.razbyte.personalassistant;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MovementActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter adapter_simple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        getLoaderManager().initLoader(0, null, this);

        final ArrayList<String> list_data = new ArrayList<String>();
        final ArrayList<String> list_move = new ArrayList<String>();
        final ArrayList<String> list_dist = new ArrayList<String>();
        String place = "";
        String dist = "";


        String[] projection = { MoveTable.COLUMN_PLACE,
                MoveTable.COLUMN_DISTANCE};
        Cursor cursor = getContentResolver().query(MoveContentProvider.CONTENT_URI, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                try {
                    place = cursor.getString(cursor
                            .getColumnIndexOrThrow(MoveTable.COLUMN_PLACE));
                    dist = cursor.getString(cursor
                            .getColumnIndexOrThrow(MoveTable.COLUMN_DISTANCE));
                }catch (Exception e) {
                   e.printStackTrace();
                }
                list_data.add(place + " " + dist);
                list_dist.add(dist);
                list_move.add(place);
            } while (cursor.moveToNext());
            // always close the cursor
            cursor.close();



           adapter_simple = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[] { MoveTable.COLUMN_PLACE},
                new int[] { android.R.id.text1});

            //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list_data);

            final ListView listView = (ListView) findViewById(R.id.LocationsList);

            listView.setAdapter(adapter_simple);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String local_place = list_move.get(position);
                        String local_dist = list_dist.get(position);

                    String phoneNo = "";

                    try {
                        FileInputStream fis = openFileInput("caller_data.txt");
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        DataInputStream dis = new DataInputStream(bis);
                        while (dis.available() != 0) {
                            phoneNo = dis.readLine();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, "I went to " + local_place + " (this is a PersonalAssistantApp generated message)", null, null);
                        Toast.makeText(getApplicationContext(), "SMS Sent!",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    // distance

                    try {
                        FileOutputStream fos = openFileOutput("log_data.txt", Context.MODE_APPEND);
                        GregorianCalendar gc = new GregorianCalendar();
                        fos.write(((gc.get(Calendar.DAY_OF_MONTH)) + "/" + (gc.get(Calendar.MONTH)+1) + "/" + (gc.get(Calendar.YEAR)) + " " + local_dist + ", ").getBytes());
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Uri uri = Uri.parse(MoveContentProvider.CONTENT_URI + "/" + id);
                    getContentResolver().delete(uri, null, null);
                    list_data.remove(position);
                    adapter_simple.notifyDataSetChanged();
                    return false;
                }
            });
        }
    }


    public void ihome(View view){
        String phoneNo = "";

        try {
            FileInputStream fis = openFileInput("caller_data.txt");
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            while(dis.available() != 0){
                phoneNo = dis.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, "I am home. (this is a PersonalAssistantApp generated message)", null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter_simple.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        adapter_simple.swapCursor(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { MoveTable.COLUMN_ID, MoveTable.COLUMN_PLACE };
        CursorLoader cursorLoader = new CursorLoader(this,
                MoveContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

}

