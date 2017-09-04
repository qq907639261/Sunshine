package com.xhbb.qinzl.sunshine.server;

/**
 * Created by qinzl on 2017/9/4.
 */

public interface JsonObjects {

    class WeatherObject {

        public Body showapi_res_body;

        public class Body {

            public Weather[] dayList;

            public class Weather {

                public String day_weather;
                public String day_wind_power;
                public String day_air_temperature;
                public String day_wind_direction;
                public String day_weather_pic;
                public String night_weather;
                public String night_wind_power;
                public String night_air_temperature;
                public String night_wind_direction;
                public String night_weather_pic;
                public String daytime;
            }
        }
    }
}
