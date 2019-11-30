package com.example.paparazziv2;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jjoe64.graphview.GraphView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Parser;

public class MapActivityreal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_activityreal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String s = intent.getStringExtra("Location");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println(s);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://cs125-cloud.cs.illinois.edu/Fall2019-MP/presets", null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("e", "   Eroor");
            }
        });
        JsonParser ob = new JsonParser();






    }

}
