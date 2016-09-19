package com.example.charliemyers.myweather;

import org.json.JSONObject;

/**
 * Created by charlie.myers on 18/09/2016.
 */
public class WeatherEntry {
    private String mTitle;
    private String mDescription;
    private String mIconName;
    private Float mTemperature;
    private String mTime;
    private String mCity;

    public WeatherEntry() {
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getIconName() {
        return mIconName;
    }

    public void setIconName(String mIconName) {
        this.mIconName = mIconName;
    }

    public Float getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Float mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }
}
