package com.example.weatherapi;

import android.widget.TextView;

public class StatResult {
    String tv_city;
    String tv_wx;
    String tv_temperature;
    String tv_minT;
    String tv_maxT;
    String tv_CI;
    String tv_pop;
    public StatResult(String tv_city,String tv_wx,String tv_temperature,String tv_minT,String tv_maxT,String tv_CI,String tv_pop) {
        this.tv_city = tv_city;
        this.tv_wx = tv_wx;
        this.tv_temperature = tv_temperature;
        this.tv_minT = tv_minT;
        this.tv_maxT = tv_maxT;
        this.tv_CI = tv_CI;
        this.tv_pop = tv_pop;
    }
}
