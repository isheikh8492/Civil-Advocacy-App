package com.imaduddinsheikh.civiladvocacyapp;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class OfficialsDownloader {
    private static final String TAG = "OfficialsDownloader";

    private static MainActivity mainActivity;

    private static RequestQueue queue;

    private static Official officialObj;

    private static String address = "6 W 31st St, Chicago, IL";

    private static final String officialAPIUrl = "https://civicinfo.googleapis.com/civicinfo/v2/representatives";
    private static final String APIKey = "AIzaSyDzLuFbK2joVKvQ-uS-ZKlSsSja4FMvZKs";

    public static void downloadOfficials(MainActivity mainActivityIn) {
        mainActivity = mainActivityIn;
        queue = Volley.newRequestQueue(mainActivity);

        Uri.Builder buildURL = Uri.parse(officialAPIUrl).buildUpon();
        buildURL.appendQueryParameter("key", APIKey);
        buildURL.appendQueryParameter("address", address);
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener =
                response -> parseJSON(response.toString());

        Response.ErrorListener error =
                error1 -> mainActivity.updateData(null);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private static void parseJSON(String s) {
        Log.d(TAG, "parseJSON:\n" + s);
    }
}
