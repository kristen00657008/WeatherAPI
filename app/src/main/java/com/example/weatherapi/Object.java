package com.example.weatherapi;

import org.json.JSONException;
import org.json.JSONObject;

public class Object {
    JSONObject object;
    String success;
    Records records;

    public Object(String string) throws JSONException {
        this.object = new JSONObject(string);
        this.success = object.getString("success");
        this.records = new Records(object.getString("records"));
    }
}
