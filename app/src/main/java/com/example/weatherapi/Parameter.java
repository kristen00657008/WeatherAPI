package com.example.weatherapi;

import org.json.JSONException;
import org.json.JSONObject;

public class Parameter {
    JSONObject parameter;
    String parameterName;
    String parameterValue;

    public Parameter(String string) throws JSONException {
        this.parameter = new JSONObject(string);
        this.parameterName = parameter.getString("parameterName");
        //this.parameterValue = parameter.getString("parameterValue");
    }
}
