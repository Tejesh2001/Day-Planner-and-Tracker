package com.example.paparazziv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//1. Save state of activities after ending them. Bar graphs not forming.
//2. Add polylines ( I know how to do just need to save the states
//3. After a day passes the lines get drawn automatically. So after each day the lines drawn are shown.

public class MainActivity extends AppCompatActivity {
    private EditText location;
    private Button club, eat, festival, social, acad;
    private List<String> categories;
    private List<Integer> clubdate, eatdate, socialdate, acaddate, festivaldate;

    private int a, b, c, d, e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        categories = new ArrayList<>();

        Button enter = findViewById(R.id.enter);
        location = findViewById(R.id.location);
        club = findViewById(R.id.Clubbing);

        Intent intent = new Intent(this, GraphActivitynew.class);
        Intent intentMap = new Intent(this, MapsActivity.class);

        enter.setOnClickListener( view -> {
            intentMap.putExtra("Location", location.getText().toString());
            startActivity(intentMap);
        });

        club.setOnClickListener(v -> {
            intent.putExtra("Button", club.getText().toString());
            a++;
            intent.putExtra("a", a);
            startActivity(intent);

        });
        eat = findViewById(R.id.eat);
        eat.setOnClickListener(v -> {

            intent.putExtra("Button", eat.getText().toString());
            b++;

            intent.putExtra("b", b);

            startActivity(intent);

        });

        festival = findViewById(R.id.festival);
        festival.setOnClickListener(v -> {
            intent.putExtra("Button", festival.getText().toString());
            c++;

            intent.putExtra("c", c);
            startActivity(intent);

        });

        social = findViewById(R.id.socializing);

        social.setOnClickListener(v -> {

            intent.putExtra("Button", social.getText().toString());
            d++;

            intent.putExtra("d", d);
            startActivity(intent);

        });
        acad = findViewById(R.id.Academics);
        acad.setOnClickListener(v -> {

            e++;
            intent.putExtra("Button", acad.getText().toString());

            intent.putExtra("e", e);

            startActivity(intent);


        });
        //GraphView graphView = findViewById(R.id.graphnew);
        //Intent i = getIntent();
        //Gson g = new Gson();
        //String s = i.getStringExtra("Series");
        //BarGraphSeries ob = g.fromJson(s, BarGraphSeries.class);

        //graphView.addSeries(ob);




    }


}








