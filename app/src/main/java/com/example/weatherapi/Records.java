package com.example.weatherapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Records {
    JSONObject records;
    String datasetDescription;
    location[] location = new location[22];

    public Records(String string) throws JSONException {
        this.records = new JSONObject(string);
        this.datasetDescription = records.getString("datasetDescription");
        JSONArray temp = new JSONArray(this.records.getString("location"));
        for(int i=0;i<22;i++){
            location[i] = new location(temp.getString(i));
        }
    }
}
