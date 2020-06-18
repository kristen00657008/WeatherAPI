package com.example.weatherapi;

import android.os.PersistableBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WeatherElement extends JSONObject {
    JSONObject weatherelement;
    String elementName;
    Time[] time = new Time[3];

    public WeatherElement(String string) throws JSONException {
        this.weatherelement =new JSONObject(string) ;
        this.elementName = weatherelement.getString("elementName");
        JSONArray temp = new JSONArray(weatherelement.getString("time"));
        for(int i=0;i<3;i++){
            time[i]= new Time(temp.getString(i));
        }

    }
}






