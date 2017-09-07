package com.xhbb.qinzl.sunshine;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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

        mSwipeRefreshLayout.setRefreshing(true);
        initRecyclerView();
        addWeatherLocationNetworkRequest();

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mWeatherAdapter);
        recyclerView.setHasFixedSize(true);
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
        mNetworkingFailedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(WeatherJson weatherJson) {
        mSwipeRefreshLayout.setRefreshing(false);
        swapWeatherList(mWeatherAdapter, weatherJson);
    }

    private void swapWeatherList(WeatherAdapter weatherAdapter, WeatherJson weatherJson) {
        List<Weather> weatherList = JsonUtils.getWeatherList(weatherJson);
        weatherAdapter.swapWeatherList(weatherList);
    }

    @Override
    public void onRefresh() {
        mNetworkingFailedTextView.setVisibility(View.GONE);
        addWeatherLocationNetworkRequest();
    }

    private class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

        private Context mContext;
        private List<Weather> mWeatherList = new ArrayList<>();

        WeatherAdapter(Context context) {
            mContext = context;
        }

        void swapWeatherList(List<Weather> weatherList) {
            mWeatherList = weatherList;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_weather, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Weather weather = mWeatherList.get(position);
            holder.bindWeather(mContext, weather);
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

            void bindWeather(final Context context, final Weather weather) {
                mWeatherTextView.setText("");

                mWeatherTextView.append(weather.getDayWeatherDescription() + "\n");
                mWeatherTextView.append(weather.getDayTemperature() + "°\n");
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
