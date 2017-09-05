package com.xhbb.qinzl.sunshine.server;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Response;
import com.xhbb.qinzl.sunshine.R;
import com.xhbb.qinzl.sunshine.common.MainSingleton;
import com.xhbb.qinzl.sunshine.server.JsonUtils.WeatherJson;

import java.util.HashMap;

/**
 * Created by qinzl on 2017/9/3.
 */

public class NetworkUtils {

    public static void addWeatherLocationRequest(Context context, String weatherLocation,
                                                 Response.Listener<WeatherJson> listener,
                                                 Response.ErrorListener errorListener,
                                                 Object requestTag) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + context.getString(R.string.aliyun_app_code));
        String url = Uri.parse("http://ali-weather.showapi.com/day15").buildUpon()
                .appendQueryParameter("area", weatherLocation)
                .build().toString();

        GsonRequest request = new GsonRequest<>(url, errorListener, WeatherJson.class, listener,
                headers);
        request.setTag(requestTag);

        MainSingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void cancelAllRequests(Context context, Object requestTag) {
        MainSingleton.getInstance(context)
                .getRequestQueue()
                .cancelAll(requestTag);
    }
}
