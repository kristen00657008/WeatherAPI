package com.example.weatherapi;

import org.json.JSONException;
import org.json.JSONObject;

public class Time {
    JSONObject time;
    Parameter parameter;
    String startTime;
    String endTime;

    public Time(String string) throws JSONException {
        this.time = new JSONObject(string);
        this.startTime = time.getString("startTime");
        this.endTime = time.getString("endTime");
        this.parameter = new Parameter(time.getString("parameter"));
    }
}
