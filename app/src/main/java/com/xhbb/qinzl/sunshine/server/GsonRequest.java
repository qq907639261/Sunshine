package com.xhbb.qinzl.sunshine.server;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by qinzl on 2017/9/4.
 */

class GsonRequest<T> extends Request<T> {

    private Gson mGson;
    private Class<T> mGsonClass;
    private Listener<T> mListener;
    private Map<String, String> mHeaders;

    GsonRequest(String url, Response.ErrorListener errorListener, Class<T> gsonClass,
                Listener<T> listener, @Nullable Map<String, String> headers) {
        super(Method.GET, url, errorListener);
        mGson = new Gson();
        mGsonClass = gsonClass;
        mListener = listener;
        mHeaders = headers;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    mGson.fromJson(json, mGsonClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }
}
