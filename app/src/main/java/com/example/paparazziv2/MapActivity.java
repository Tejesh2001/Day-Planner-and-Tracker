package com.example.paparazziv2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapActivity {


    public void map() {

        RequestQueue requestQueue = Volley.newRequestQueue(new MainActivity());
        Intent intent = new Intent(new MainActivity(), MainActivity.class);
        String s = intent.getStringExtra("Location");
        System.out.println(s);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://cs125-cloud.cs.illinois.edu/Fall2019-MP/presets/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray presets = response.getJSONArray("presets");
                            for (int i = 0; i < presets.length(); i++) {
                                String name = response.get("name").toString();
                                System.out.println(name + "  naem found");

                            }


                        } catch (Exception e) {
                            Log.e("error", "Error in getting json array");
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("F", "fff");
            }
        });
        requestQueue.add(jsonObjectRequest);


    }
}
