package com.xhbb.qinzl.sunshine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WeatherDetailActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, WeatherDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
    }
}
