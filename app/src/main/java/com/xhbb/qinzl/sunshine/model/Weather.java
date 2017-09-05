package com.xhbb.qinzl.sunshine.model;

/**
 * Created by qinzl on 2017/9/4.
 */

public class Weather {

    private String mDayWeatherDescription;
    private String mDayWindPowerDescription;
    private String mDayTemperature;
    private String mDayWeatherIconUrl;
    private String mNightWeatherDescription;
    private String mNightWindPowerDescription;
    private String mNightTemperature;
    private String mNightWeatherIconUrl;

    public String getDayWeatherDescription() {
        return mDayWeatherDescription;
    }

    public void setDayWeatherDescription(String dayWeatherDescription) {
        mDayWeatherDescription = dayWeatherDescription;
    }

    public String getDayWindPowerDescription() {
        return mDayWindPowerDescription;
    }

    public void setDayWindPowerDescription(String dayWindPowerDescription) {
        mDayWindPowerDescription = dayWindPowerDescription;
    }

    public String getDayTemperature() {
        return mDayTemperature;
    }

    public void setDayTemperature(String dayTemperature) {
        mDayTemperature = dayTemperature;
    }

    public String getDayWeatherIconUrl() {
        return mDayWeatherIconUrl;
    }

    public void setDayWeatherIconUrl(String dayWeatherIconUrl) {
        mDayWeatherIconUrl = dayWeatherIconUrl;
    }
}
