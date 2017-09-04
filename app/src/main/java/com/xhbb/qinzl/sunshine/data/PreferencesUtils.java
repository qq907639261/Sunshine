package com.xhbb.qinzl.sunshine.data;

import android.content.Context;
import android.preference.PreferenceManager;

import com.xhbb.qinzl.sunshine.R;

/**
 * Created by qinzl on 2017/9/3.
 */

public class PreferencesUtils {

    public static void saveWeatherLocation(Context context, String weatherLocation) {
        String key = context.getString(R.string.key_weather_location);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(key, weatherLocation)
                .apply();
    }

    public static String getWeatherLocation(Context context) {
        String key = context.getString(R.string.key_weather_location);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, "深圳");
    }
}
