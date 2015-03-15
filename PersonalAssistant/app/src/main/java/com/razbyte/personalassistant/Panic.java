package com.razbyte.personalassistant;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Panic extends Activity{

    String phoneNo;
    String sms = "Panic alert was activated on this device! Please contact the owner immediately! ";
    String ptext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic);

        // location

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            sms = sms.concat("Location: " + location.getLatitude() + "," + location.getLongitude() + "(Auto Message: PersonalAssistantApp)");
        }
        catch (Exception e){
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                sms = sms.concat("Location: " + location.getLatitude() + "," + location.getLongitude() + "(Auto Message: PersonalAssistantApp)");
            }
            catch (Exception e2){}
        }



        try {
            FileInputStream fis = openFileInput("panic_data.txt");
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            while(dis.available() != 0){
                ptext = dis.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent to " + phoneNo,
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        TextView tv = (TextView)(findViewById(R.id.text_panic));
        tv.setText(ptext);

    }
}
