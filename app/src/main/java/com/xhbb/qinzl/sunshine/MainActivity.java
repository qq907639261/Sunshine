package com.xhbb.qinzl.sunshine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xhbb.qinzl.sunshine.data.PreferencesUtils;
import com.xhbb.qinzl.sunshine.server.JsonObjects.WeatherObject;
import com.xhbb.qinzl.sunshine.server.NetworkUtils;

public class MainActivity extends AppCompatActivity
        implements Response.Listener<WeatherObject>, Response.ErrorListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";

    private TextView mWeatherTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mWeatherTextView = (TextView) findViewById(R.id.weatherTextView);

        mSwipeRefreshLayout.setRefreshing(true);
        addWeatherLocationNetworkRequest();

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void addWeatherLocationNetworkRequest() {
        NetworkUtils.cancelAllRequests(this, TAG);

        String weatherLocation = PreferencesUtils.getWeatherLocation(this);
        NetworkUtils.addWeatherLocationRequest(this, weatherLocation, this, this, TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.cancelAllRequests(this, TAG);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mSwipeRefreshLayout.setRefreshing(false);
        mWeatherTextView.setText(R.string.networking_failed_text);
    }

    @Override
    public void onResponse(WeatherObject response) {
        mSwipeRefreshLayout.setRefreshing(false);
        mWeatherTextView.setText("");

        for (WeatherObject.Body.Weather weather : response.showapi_res_body.dayList) {
            mWeatherTextView.append(weather.daytime + "\n");
            mWeatherTextView.append(weather.day_weather + "\n");
            mWeatherTextView.append(weather.day_air_temperature + "\n");
            mWeatherTextView.append(weather.day_wind_power + "\n");
            mWeatherTextView.append(weather.day_wind_direction + "\n");
            mWeatherTextView.append(weather.day_weather_pic + "\n\n");
        }
    }

    @Override
    public void onRefresh() {
        addWeatherLocationNetworkRequest();
    }
}
