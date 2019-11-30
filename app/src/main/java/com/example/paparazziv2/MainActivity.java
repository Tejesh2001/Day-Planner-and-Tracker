package com.example.paparazziv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {
    private TextView location;
    private Button club, eat, festival, social, acad;
    private List<String> categories;
    private List<Integer> clubdate, eatdate, socialdate, acaddate, festivaldate;

    private int a, b, c, d, e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        categories = new ArrayList<>();


        //Button enter = findViewById(R.id.enter);
        location = findViewById(R.id.location);
        club = findViewById(R.id.Clubbing);

        Intent intent = new Intent(this, GraphActivitynew.class);
        Intent intentmap = new Intent(this, MapActivityreal.class);



        intentmap.putExtra("Location", location.getText().toString());

        club.setOnClickListener(v -> {
            intent.putExtra("Button", club.getText().toString());


            startActivity(intent);


        });
        eat = findViewById(R.id.eat);
        eat.setOnClickListener(v -> {

            intent.putExtra("Button", eat.getText().toString());


            startActivity(intent);


        });
        festival = findViewById(R.id.festival);
        festival.setOnClickListener(v -> {
            intent.putExtra("Button", festival.getText().toString());

            startActivity(intent);


        });
        social = findViewById(R.id.socializing);

        social.setOnClickListener(v -> {

            intent.putExtra("Button", social.getText().toString());

            startActivity(intent);

        });
        acad = findViewById(R.id.Academics);
        acad.setOnClickListener(v -> {


            intent.putExtra("Button", acad.getText().toString());

            startActivity(intent);


        });


    }


}








