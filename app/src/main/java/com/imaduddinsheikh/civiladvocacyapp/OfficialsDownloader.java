package com.imaduddinsheikh.civiladvocacyapp;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfficialsDownloader {
    private static final String TAG = "OfficialsDownloader";

    private static MainActivity mainActivity;

    private static RequestQueue queue;

    private static Official officialObj;

    private static List<Official> officialList = new ArrayList<>();;

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

        try {
            JSONObject jObjMain = new JSONObject(s);

            // "offices" and "official" section
            JSONArray offices = jObjMain.getJSONArray("offices");
            JSONArray officials = jObjMain.getJSONArray("officials");

            for (int i = 0; i < offices.length(); i++) {
                JSONObject office = offices.getJSONObject(i);
                String officialOfficeTitle = office.getString("name");
                JSONArray officialIndex = office.getJSONArray("officialIndices");

                if (officialIndex.length() > 1) {
                    for (int j = 0; j < officialIndex.length(); j++) {
                        JSONObject official = officials.getJSONObject(officialIndex.getInt(j));
                        mergeOfficialToList(official, officialOfficeTitle);

                    }
                } else {
                    JSONObject official = officials.getJSONObject(officialIndex.getInt(0));
                    mergeOfficialToList(official, officialOfficeTitle);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mergeOfficialToList(JSONObject official, String officialTitle) {

        try {
            // "name" section
            String officialName = official.getString("name");

            // "party" section
            String officialParty = "Unknown";
            if (official.has("party")) {
                officialParty = official.getString("party");
            }

            // "address" section
            String officialAddress = null;
            StringBuilder addressLine = new StringBuilder();
            if (official.has("address")) {
                JSONObject address = official.getJSONArray("address")
                        .getJSONObject(0);
                for (int a = 1; a <= address.length(); a++) {
                    String lineAttr = "line" + a;
                    if (address.has(lineAttr)) {
                        addressLine.append(address.get(lineAttr)).append(", ");
                    } else {
                        break;
                    }
                }
                addressLine.append(address.get("city")).append(", ")
                        .append(address.get("state")).append(" ").append(address.get("zip"));
                officialAddress = addressLine.toString();
            }

            // "phone" section
            String officialPhone = null;
            if (official.has("phones")) {
                officialPhone = official.getJSONArray("phones").getString(0);
            }

            // "website" section
            String officialWebSite = null;
            if (official.has("urls")) {
                officialWebSite = official.getJSONArray("urls").getString(0);
            }

            // "email" section
            String officialEmail = null;
            if (official.has("emails")) {
                officialEmail = official.getJSONArray("emails").getString(0);
            }

            officialObj = new Official(officialName, officialTitle, officialParty, officialAddress,
                    officialPhone, officialEmail, officialWebSite);

            officialList.add(officialObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
