package com.example.paparazziv2;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.ByteArrayBuffer;
//
//import org.json.JSONObject;
//import org.json.JSONException;
//import org.json.JSONArray;

//import java.io.BufferedInputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;

/**
 * Helper class to use the google places api.
 */
public class PlacesAPI {
    private static String LOG_TAG = "PlacesAPI";

    /** The base of the places API. */
    static final String API_BASE = "https://maps.googleapis.com/maps/api/place/findplacefromtext/";
    static final String OUTPUT_TYPE_JSON = "json";
    static final String INPUT_TYPE = "textquery";
    static final String API_KEY = "AIzaSyC1__M1ff-99MrCEfi0B0CB6PByZi3AJOg";
    static final String PARAMETER_GEOMETRY = "geometry";

    /**
     * Perform a search on the google map Places API.
     * <p>
     * Code from StackOverflow thread
     * https://stackoverflow.com/questions/12460471/how-to-send-a-google-places-search-request-with-java.
     * Code written by user "saxman". Code has been modified for this application.
     *
     * @param keyword The key word of this search.
     * @return The location, as a LatLng object, of the best result (the first in the
     *         result JsonArray).
     */
    public static LatLng search(final String keyword) {
        LatLng result = null;

        // Example URL
        // https://maps.googleapis.com/maps/api/place/findplacefromtext/, API_BASE
        // json?input=mongolian%20grill&inputtype=textquery
        // &fields=photos,formatted_address,name,opening_hours,rating
        // &locationbias=circle:2000@47.6918452,-122.2226413&key=YOUR_API_KEY

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(API_BASE);
            sb.append(OUTPUT_TYPE_JSON + "?");
            sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
            sb.append("&inputtype=" + INPUT_TYPE);
            sb.append("&fields=" + PARAMETER_GEOMETRY);
            sb.append("&key=" + API_KEY);
            // sb.append("&location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            // sb.append("&radius=" + String.valueOf(radius));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return result;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return result;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

//        try {
//            // Create a JSON object hierarchy from the results
//            JSONObject jsonObj = new JSONObject(jsonResults.toString());
//            JSONArray predsJsonArray = jsonObj.getJSONArray("results");
//
//            // Extract the Place descriptions from the results
//            resultList = new ArrayList<Place>(predsJsonArray.length());
//            for (int i = 0; i < predsJsonArray.length(); i++) {
//                Place place = new Place();
//                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
//                place.name = predsJsonArray.getJSONObject(i).getString("name");
//                resultList.add(place);
//            }
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, "Error processing JSON results", e);
//        }

        JsonObject location;
        JsonObject searchResult = new JsonParser().parse(jsonResults.toString()).getAsJsonObject();
        try {
            String status = searchResult.get("status").getAsString();
            Log.i(LOG_TAG, "Status of the request result is " + status);
            if (status.equals("OK")) {
                location = searchResult.get("candidates").getAsJsonArray().get(0).getAsJsonObject()
                        .get("location").getAsJsonObject();
                result = new LatLng(location.get("lat").getAsDouble(),
                        location.get("lng").getAsDouble());
            } else {
                String errorMessage = searchResult.get("error_message").getAsString();
                Log.e(LOG_TAG, "Search has failed. Cause of failure: " + errorMessage);
                return null;
            }
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Error processing API response", e);
            return null;
        }

        return result;
    }
}
