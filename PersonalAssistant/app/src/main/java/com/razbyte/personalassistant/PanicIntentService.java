package com.razbyte.personalassistant;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.SmsManager;
import android.widget.Toast;


public class PanicIntentService extends IntentService {

        public static final String PARAM_IN_MSG = "numar";

        public PanicIntentService() {
            super("PanicIntentService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String phoneNo = intent.getStringExtra(PARAM_IN_MSG);
            String sms = "Panic alert was activated on this device! Please contact the owner immediately! ";

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
            Toast.makeText(this, sms, Toast.LENGTH_LONG).show();


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
        }
}

