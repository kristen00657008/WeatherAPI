package com.example.weatherapi;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ConnThread {
    Activity activity;
    ProgressBar pb;
    TextView tv_city;
    TextView tv_temperature;
    TextView tv_wx;
    TextView tv_minT;
    TextView tv_maxT;
    TextView tv_CI;
    TextView tv_pop;
    Object JSON;
    Double lat;
    Double lng;
    public ConnThread(Activity activity,ProgressBar pb, TextView tv_city,TextView tv_wx,TextView tv_temperature,TextView tv_minT,TextView tv_maxT,TextView tv_CI,TextView tv_pop,Double lat,Double lng)
    {
        setActivity(activity);
        setProgressBar(pb);
        setLocation(lat,lng);
        setTextView(tv_city,tv_wx,tv_temperature,tv_minT,tv_maxT,tv_CI,tv_pop);
        setCityNum(lat,lng);
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            try {
                URL url = new URL("https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-38A5972D-6400-4127-9A58-852965CBAC44");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10 * 1000);
                conn.setReadTimeout(10 * 1000);
                conn.connect();
                int statusCode = conn.getResponseCode();
                if(statusCode == 200){
                    String resStr = getJSONEntity(conn);
                    if (resStr != null) {
                       statistics(resStr);
                        return;
                    }
                }
            } catch (Exception e){
                Log.d("error", String.valueOf(e));
            }
            connerror();
        }
    };

    public void statistics(String resStr) throws JSONException { //JSON.records.location[?].weatherelement[?].time[?].parameter.parameterName
        JSON = new Object(resStr);
        //Log.d("Chase99", String.valueOf(JSON.records.location[0].weatherelement[2].time[0].parameter.parameterName));

        connsucc(setCityWeather());
    }

    public void connerror() {
        activity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(activity, "連線失敗", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void connsucc(final StatResult result) {
        activity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                tv_city.setText(result.tv_city);
                tv_wx.setText(result.tv_wx);
                tv_temperature.setText(result.tv_temperature);
                tv_minT.setText(result.tv_minT);
                tv_maxT.setText(result.tv_maxT);
                tv_CI.setText(result.tv_CI);
                tv_pop.setText(result.tv_pop);
                pb.setVisibility(View.GONE);
            }
        });
    }

    public String getJSONEntity(HttpURLConnection conn) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte [] buffer = new byte[1024] ;
            int len = 0;
            do {
                len = conn.getInputStream().read(buffer);
                if (len > 0) {
                    baos.write(buffer, 0, len);
                }
            } while (len > 0);
            String jsonString = baos.toString();
            baos.close();
            conn.getInputStream().close();
            return jsonString;
        } catch( Exception e) {
        }
        return null;
    }

    public StatResult setCityWeather(){
        int cityNum = setCityNum(lat,lng);
        String tv_city = JSON.records.location[cityNum].locationName;
        String tv_wx = JSON.records.location[cityNum].weatherelement[0].time[1].parameter.parameterName;
        String tv_temperature = " " + JSON.records.location[cityNum].weatherelement[2].time[1].parameter.parameterName + "°";
        String tv_minT = JSON.records.location[cityNum].weatherelement[2].time[1].parameter.parameterName + "°C";
        String tv_maxT = JSON.records.location[cityNum].weatherelement[4].time[1].parameter.parameterName + "°C";
        String tv_CI = JSON.records.location[cityNum].weatherelement[3].time[1].parameter.parameterName;
        String tv_pop = JSON.records.location[cityNum].weatherelement[1].time[1].parameter.parameterName + "%";

        return new StatResult(tv_city,tv_wx,tv_temperature,tv_minT,tv_maxT,tv_CI,tv_pop);
    }
    public  int setCityNum(Double lat,Double lng){
        int cityNum = 18;
        BigDecimal latB   =   new   BigDecimal(lat);
        BigDecimal lngB   =   new   BigDecimal(lng);
        double   newLat   =   latB.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        double   newLng   =   lngB.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        Log.d("Chase997", String.valueOf(newLat));
        Log.d("Chase998", String.valueOf(newLng));


        /*if(newLng>121.17){
            if(newLat >= 24.28){
                cityNum = 7;
            }else if(newLat >= 23.10){
                cityNum = 10;
            }else if(newLat >= 22.25){
                cityNum = 12;
            }
        }*/
        if(newLng>120.0){
            if(newLat >= 25.04){
                cityNum = 18;
            }else if(newLat>=24.82){
                if(newLng >= 121.50){
                    cityNum = 5;
                }else if(newLng >= 121.39){
                    cityNum = 1;
                }else {
                    cityNum = 13;
                }
            }
            else if(newLat >= 24.60){
                cityNum = 4;
            }else if(newLat >= 24.33){
                cityNum = 8;
            }else if(newLat >= 24.08){
                cityNum = 11;
            }else if(newLat >= 23.78){
                cityNum = 20;
            }else if(newLat >= 23.57){
                cityNum = 9;
            }else if(newLat >= 23.31){
                cityNum = 0;
            }else if(newLat >= 22.89){
                cityNum = 6;
            }else if(newLat >= 22.54){
                cityNum = 15;
            }else {
                cityNum = 17;
            }
        }
        else{
            if(newLat >= 25.5){
                cityNum = 21;
            }else if(newLat >= 24.00){
                cityNum = 16;
            }else{
                cityNum = 19;
            }
        }
        return  cityNum;
    }
    public void setLocation(Double lat,Double lng){ this.lat = lat; this.lng = lng;}
    public void setActivity(Activity activity){
        this.activity = activity;
    }
    public void setProgressBar(ProgressBar pb){
        this.pb = pb;
    }
    public void setTextView(TextView tv_city,TextView tv_wx,TextView tv_temperature,TextView tv_minT,TextView tv_maxT,TextView tv_CI,TextView tv_pop){
        this.tv_city = tv_city;
        this.tv_temperature = tv_temperature;
        this.tv_wx = tv_wx;
        this.tv_minT = tv_minT;
        this.tv_maxT = tv_maxT;
        this.tv_CI = tv_CI;
        this.tv_pop = tv_pop;
    }
}
