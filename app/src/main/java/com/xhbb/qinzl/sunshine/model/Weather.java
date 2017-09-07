package com.xhbb.qinzl.sunshine.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qinzl on 2017/9/4.
 */

public class Weather implements Parcelable {

    private String mDayWeatherDescription;
    private String mDayWindPowerDescription;
    private String mDayTemperature;
    private String mDayWeatherIconUrl;
    private String mDayWindDirection;
    private String mNightWeatherDescription;
    private String mNightWindPowerDescription;
    private String mNightTemperature;
    private String mNightWeatherIconUrl;
    private String mNightWindDirection;
    private String mSunriseAndSunsetTime;
    private int mDayOfWeek;

    public Weather() {
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mDayWeatherDescription);
        parcel.writeString(mDayWindPowerDescription);
        parcel.writeString(mDayTemperature);
        parcel.writeString(mDayWeatherIconUrl);
        parcel.writeString(mDayWindDirection);
        parcel.writeString(mNightWeatherDescription);
        parcel.writeString(mNightWindPowerDescription);
        parcel.writeString(mNightTemperature);
        parcel.writeString(mNightWeatherIconUrl);
        parcel.writeString(mNightWindDirection);
        parcel.writeString(mSunriseAndSunsetTime);
        parcel.writeInt(mDayOfWeek);
    }

    private Weather(Parcel in) {
        mDayWeatherDescription = in.readString();
        mDayWindPowerDescription = in.readString();
        mDayTemperature = in.readString();
        mDayWeatherIconUrl = in.readString();
        mDayWindDirection = in.readString();
        mNightWeatherDescription = in.readString();
        mNightWindPowerDescription = in.readString();
        mNightTemperature = in.readString();
        mNightWeatherIconUrl = in.readString();
        mNightWindDirection = in.readString();
        mSunriseAndSunsetTime = in.readString();
        mDayOfWeek = in.readInt();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
