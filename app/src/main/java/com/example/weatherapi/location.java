package com.example.weatherapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class location {
    JSONObject location;
    String locationName;
    WeatherElement[] weatherelement = new WeatherElement[5];

    public location(String string) throws JSONException {
        this.location = new JSONObject(string);
        this.locationName = location.getString("locationName");
        JSONArray temp = new JSONArray(location.getString("weatherElement"));
        for(int i=0;i<5;i++){
            this.weatherelement[i] = new WeatherElement(temp.getString(i));
        }

    }
}
