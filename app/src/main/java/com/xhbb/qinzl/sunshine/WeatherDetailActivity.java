package com.xhbb.qinzl.sunshine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.xhbb.qinzl.sunshine.model.Weather;

public class WeatherDetailActivity extends AppCompatActivity {

    private static final String EXTRA_WEATHER = BuildConfig.APPLICATION_ID + ".EXTRA_WEATHER";

    private TextView mWeatherDetailTextView;

    public static void start(Context context, Weather weather) {
        Intent starter = new Intent(context, WeatherDetailActivity.class);
        starter.putExtra(EXTRA_WEATHER, weather);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        Weather weather = getIntent().getParcelableExtra(EXTRA_WEATHER);

        mWeatherDetailTextView = (TextView) findViewById(R.id.weatherDetailTextView);

        mWeatherDetailTextView.append(weather.getDayWindPowerDescription() + "\n");
        mWeatherDetailTextView.append(weather.getDayTemperature() + "°\n");
        mWeatherDetailTextView.append(weather.getDayWeatherDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareWeatherInfo();
                return true;
            case R.id.action_setting:
                SettingsActivity.start(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareWeatherInfo() {
        String text = mWeatherDetailTextView.getText().toString();
        Intent intent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle(R.string.title_share_weather_chooser)
                .setText(text)
                .createChooserIntent();

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.toast_no_matched_app, Toast.LENGTH_SHORT).show();
        }
    }
}
