package com.xhbb.qinzl.sunshine.data;

import android.content.Context;
import android.preference.PreferenceManager;

import com.xhbb.qinzl.sunshine.R;

/**
 * Created by qinzl on 2017/9/3.
 */

public class PreferencesUtils {

    public static String getWeatherLocation(Context context) {
        String key = context.getString(R.string.pref_key_weather_location);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, context.getString(R.string.pref_default_weather_location));
    }

    public static String getTemperatureUnitValue(Context context) {
        String key = context.getString(R.string.pref_key_temperature_unit);
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(key, context.getString(R.string.pref_entry_value_metric));
    }
}
