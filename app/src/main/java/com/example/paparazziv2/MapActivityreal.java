package com.example.paparazziv2;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MapActivityreal extends AppCompatActivity  {
    private GoogleMap squirrelmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_activityreal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        GoogleMap squirrelmap = null;
        String s = intent.getStringExtra("Location");
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        System.out.println(s);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://cs125-cloud.cs.illinois.edu/Fall2019-MP/presets/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            JsonParser ob = new JsonParser();
                            JsonElement element = ob.parse(response);
                            JsonObject jsonObject = element.getAsJsonObject();
                            JsonArray presets = jsonObject.getAsJsonArray();
                            for (JsonElement i : presets.get(1).getAsJsonArray()) {

                                if (s.equals(i.getAsJsonObject().getAsString())) {

                                    LatLng position = new LatLng(i.getAsJsonObject().get("latitude").getAsDouble(),
                                            i.getAsJsonObject().get("latitude").getAsDouble());
                                    MarkerOptions options = new MarkerOptions().position(position);


                                    //Marker marker = map.addMarker(options);
                                }





                            }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


    }

}
