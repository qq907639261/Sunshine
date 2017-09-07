package com.xhbb.qinzl.sunshine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xhbb.qinzl.sunshine.model.Weather;

public class WeatherDetailActivity extends AppCompatActivity {

    private static final String EXTRA_WEATHER = BuildConfig.APPLICATION_ID + ".EXTRA_WEATHER";

    public static void start(Context context, Weather weather) {
        Intent starter = new Intent(context, WeatherDetailActivity.class);
        starter.putExtra(EXTRA_WEATHER, weather);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        TextView weatherDetailTextView = (TextView) findViewById(R.id.weatherDetailTextView);

        Weather weather = getIntent().getParcelableExtra(EXTRA_WEATHER);
        weatherDetailTextView.append(weather.getDayWindPowerDescription() + "\n");
        weatherDetailTextView.append(weather.getDayTemperature() + "°\n");
        weatherDetailTextView.append(weather.getDayWeatherDescription());
    }
}
