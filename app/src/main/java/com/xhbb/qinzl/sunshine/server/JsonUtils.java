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
        addWeather(weatherList, weatherJson.showapi_res_body.f1);
        addWeather(weatherList, weatherJson.showapi_res_body.f2);
        addWeather(weatherList, weatherJson.showapi_res_body.f3);
        addWeather(weatherList, weatherJson.showapi_res_body.f4);
        addWeather(weatherList, weatherJson.showapi_res_body.f5);
        addWeather(weatherList, weatherJson.showapi_res_body.f6);
        addWeather(weatherList, weatherJson.showapi_res_body.f7);

        return weatherList;
    }

    private static void addWeather(List<Weather> weatherList,
                                   WeatherJson.Body.WeatherObject weatherObject) {
        Weather weather = new Weather();
        weather.setDayWeatherDescription(weatherObject.day_weather);
        weather.setDayWindPowerDescription(weatherObject.day_wind_power);
        weather.setDayTemperature(weatherObject.day_air_temperature);
        weather.setDayWeatherIconUrl(weatherObject.day_weather_pic);
        weather.setNightWeatherDescription(weatherObject.night_weather);
        weather.setNightWindPowerDescription(weatherObject.night_wind_power);
        weather.setNightTemperature(weatherObject.night_air_temperature);
        weather.setNightWeatherIconUrl(weatherObject.night_weather_pic);
        weather.setDayOfWeek(weatherObject.weekday);
        weather.setSunriseAndSunsetTime(weatherObject.sun_begin_end);

        weatherList.add(weather);
    }

    public class WeatherJson {

        Body showapi_res_body;

        class Body {

            WeatherObject f1;
            WeatherObject f2;
            WeatherObject f3;
            WeatherObject f4;
            WeatherObject f5;
            WeatherObject f6;
            WeatherObject f7;

            class WeatherObject {

                String day_weather;
                String day_wind_power;
                String day_air_temperature;
                String day_weather_pic;
                String night_weather;
                String night_wind_power;
                String night_air_temperature;
                String night_weather_pic;
                String sun_begin_end;
                int weekday;
            }
        }
    }
}
