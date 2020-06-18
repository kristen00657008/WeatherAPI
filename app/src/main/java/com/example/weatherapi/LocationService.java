package com.example.weatherapi;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.location.LocationListener;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class LocationService extends Service implements LocationListener{

    LocationManager locationManager;
    Double lat = 0.0;
    Double lng = 0.0;

    @Override
    public IBinder onBind(Intent intent)  {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId ){
        initLoc();

        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    private void initLoc() {
        locationManager = (LocationManager)getSystemService(AppCompatActivity.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000, 1f, (LocationListener) this);
        } catch (SecurityException e) {
        }
    }
    @Override
    public void onLocationChanged(Location p0) {
        if (p0 == null) {
            return;
        }
        lat = p0.getLatitude();
        lng = p0.getLongitude();
        Bundle message = new Bundle();
        message.putDouble("lat", lat);
        message.putDouble("lng", lng);
        Intent intent = new Intent("FilterString");
        intent.putExtras(message);
        sendBroadcast(intent);
    }

    @Override
    public void onProviderDisabled(String p0) {

    }
    @Override
    public void onProviderEnabled(String p0) {
    }
    @Override
    public void onStatusChanged(String p0,int p1,Bundle p2) {
    }

}
