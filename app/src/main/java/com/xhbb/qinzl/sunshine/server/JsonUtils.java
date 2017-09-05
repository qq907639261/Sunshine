package com.xhbb.qinzl.sunshine.server;

import com.xhbb.qinzl.sunshine.model.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinzl on 2017/9/4.
 */

public class JsonUtils {

    public static List<Weather> getWeatherList(WeatherJson weatherJson) {
        List<Weather> weatherList = new ArrayList<>();

        for (WeatherJson.Body.WeatherObject weatherObject : weatherJson.showapi_res_body.dayList) {
            Weather weather = new Weather();
            weather.setDayWeatherDescription(weatherObject.day_weather);
            weather.setDayWindPowerDescription(weatherObject.day_wind_power);
            weather.setDayTemperature(weatherObject.day_air_temperature);
            weather.setDayWeatherIconUrl(weatherObject.day_weather_pic);

            weatherList.add(weather);
        }

        return weatherList;
    }

    public class WeatherJson {

        Body showapi_res_body;

        class Body {

            WeatherObject[] dayList;

            class WeatherObject {

                String day_weather;
                String day_wind_power;
                String day_air_temperature;
                String day_weather_pic;
                String night_weather;
                String night_wind_power;
                String night_air_temperature;
                String night_weather_pic;
                String daytime;
            }
        }
    }
}
