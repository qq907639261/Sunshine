package com.xhbb.qinzl.sunshine;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xhbb.qinzl.sunshine.data.PreferencesUtils;
import com.xhbb.qinzl.sunshine.model.Weather;
import com.xhbb.qinzl.sunshine.server.JsonUtils;
import com.xhbb.qinzl.sunshine.server.JsonUtils.WeatherJson;
import com.xhbb.qinzl.sunshine.server.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements Response.Listener<WeatherJson>, Response.ErrorListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";

    private TextView mNetworkingFailedTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WeatherAdapter mWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mNetworkingFailedTextView = (TextView) findViewById(R.id.networkingFailedTextView);
        mWeatherAdapter = new WeatherAdapter(this);

        initRecyclerView();

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mWeatherAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mSwipeRefreshLayout.setRefreshing(true);
        String temperatureUnitValue = PreferencesUtils.getTemperatureUnitValue(this);

        mWeatherAdapter.setTemperatureUnitValue(temperatureUnitValue);
        addWeatherLocationNetworkRequest();
    }

    private void addWeatherLocationNetworkRequest() {
        NetworkUtils.cancelAllRequests(this, TAG);

        String weatherLocation = PreferencesUtils.getWeatherLocation(this);
        NetworkUtils.addWeatherLocationRequest(this, weatherLocation, this, this, TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkUtils.cancelAllRequests(this, TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                SettingsActivity.start(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNetworkingFailedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(WeatherJson weatherJson) {
        mSwipeRefreshLayout.setRefreshing(false);

        List<Weather> weatherList = JsonUtils.getWeatherList(weatherJson);
        mWeatherAdapter.swapWeatherList(weatherList);
    }

    @Override
    public void onRefresh() {
        mNetworkingFailedTextView.setVisibility(View.GONE);
        addWeatherLocationNetworkRequest();
    }

    private class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

        private Context mContext;
        private List<Weather> mWeatherList = new ArrayList<>();
        private String mTemperatureUnitValue;

        WeatherAdapter(Context context) {
            mContext = context;
        }

        void swapWeatherList(List<Weather> weatherList) {
            mWeatherList = weatherList;
            notifyDataSetChanged();
        }

        void setTemperatureUnitValue(String temperatureUnitValue) {
            mTemperatureUnitValue = temperatureUnitValue;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_weather, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Weather weather = mWeatherList.get(position);
            holder.bindWeather(mContext, weather, mTemperatureUnitValue);
        }

        @Override
        public int getItemCount() {
            return mWeatherList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mWeatherTextView;

            ViewHolder(View itemView) {
                super(itemView);
                mWeatherTextView = (TextView) itemView;
            }

            void bindWeather(final Context context, final Weather weather,
                             String temperatureUnitValue) {
                mWeatherTextView.setText("");
                int temperature = Integer.valueOf(weather.getDayTemperature());

                String formattedTemperature;
                if (temperatureUnitValue.equals(getString(R.string.pref_entry_value_metric))) {
                    formattedTemperature = getString(R.string.format_temperature_metric, temperature);
                } else {
                    temperature = (int) (temperature * 1.8) + 32;
                    formattedTemperature = getString(R.string.format_temperature_imperial, temperature);
                }

                mWeatherTextView.append(weather.getDayWeatherDescription() + "\n");
                mWeatherTextView.append(formattedTemperature + "\n");
                mWeatherTextView.append(weather.getDayWindPowerDescription());

                mWeatherTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WeatherDetailActivity.start(context, weather);
                    }
                });
            }
        }
    }
}
