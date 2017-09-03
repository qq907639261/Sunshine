package com.xhbb.qinzl.sunshine.server;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.xhbb.qinzl.sunshine.common.MainSingleton;

/**
 * Created by qinzl on 2017/9/3.
 */

public class NetworkUtils {

    public static void addWeatherLocationRequest(Context context, Response.Listener<String> listener,
                                                 Response.ErrorListener errorListener,
                                                 String weatherLocation, Object requestTag) {
        String url = Uri.parse("https://andfun-weather.udacity.com/weather")
                .buildUpon()
                .appendQueryParameter("q", weatherLocation)
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("cnt", "14")
                .build()
                .toString();

        StringRequest request = new StringRequest(Request.Method.GET, url, listener, errorListener);
        request.setTag(requestTag);

        MainSingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void cancelAllRequests(Context context, Object requestTag) {
        MainSingleton.getInstance(context)
                .getRequestQueue()
                .cancelAll(requestTag);
    }
}
