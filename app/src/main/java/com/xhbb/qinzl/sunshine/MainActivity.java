package com.xhbb.qinzl.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xhbb.qinzl.sunshine.data.PreferencesUtils;
import com.xhbb.qinzl.sunshine.server.NetworkUtils;

public class MainActivity extends AppCompatActivity
        implements Response.Listener<String>, Response.ErrorListener {

    private static final String TAG = "MainActivity";

    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherTextView = (TextView) findViewById(R.id.weatherTextView);

        addWeatherLocationNetworkRequest();
    }

    private void addWeatherLocationNetworkRequest() {
        String weatherLocation = PreferencesUtils.getWeatherLocation(this);
        NetworkUtils.addWeatherLocationRequest(this, this, this, TAG, weatherLocation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkUtils.cancelAllRequests(this, TAG);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}
