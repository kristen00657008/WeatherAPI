package com.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb_loading;
    TextView tv_city;
    TextView tv_temperature;
    TextView tv_wx;
    TextView tv_minT;
    TextView tv_maxT;
    TextView tv_CI;
    TextView tv_pop;
    Boolean getPermission = false;
    String[] list = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    Double lat;
    Double lng;
    public Handler handler = new Handler( );
    public Runnable runnable;
    int cityNum;
    ConnThread connthread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findTextView();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    list ,
                    1);
        } else {
            getPermission = true;
        }

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 處理 Service 傳來的訊息。
                Bundle message = intent.getExtras();
                lat = message.getDouble("lat");
                lng = message.getDouble("lng");
                Create();
            }
        };
        final String Action = "FilterString";
        IntentFilter filter = new IntentFilter(Action);
        registerReceiver(receiver, filter);
    }
    public  void Create(){
        connthread = new ConnThread(this, pb_loading, tv_city,tv_wx, tv_temperature,tv_minT,tv_maxT,tv_CI,tv_pop,lat,lng);
    }

    public void startLocate() {

        Intent intent = new Intent(MainActivity.this,
                LocationService.class);
        this.startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode ,String[] permissions
            , int [] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            startLocate();

            getPermission = true;
        }

    }

    public void findTextView(){
        pb_loading = findViewById(R.id.pb_loading);
        tv_city = findViewById(R.id.tv_city);
        tv_wx = findViewById(R.id.tv_wx);
        tv_temperature = findViewById(R.id.tv_temperature);
        tv_minT = findViewById(R.id.tv_minT);
        tv_maxT = findViewById(R.id.tv_maxT );
        tv_CI = findViewById(R.id.tv_CI);
        tv_pop = findViewById(R.id.tv_pop);
    }


}