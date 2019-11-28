package com.example.paparazziv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        GraphView graph = findViewById(R.id.graph);


        Button enter = findViewById(R.id.enter);
        location = findViewById(R.id.location);
        club = findViewById(R.id.Clubbing);
        System.out.println(club.getText().toString());
        System.out.println(categories.size() + "  size before add");
        clubdate = new ArrayList<>();
        eatdate = new ArrayList<>();
        festivaldate = new ArrayList<>();
        socialdate = new ArrayList<>();
        acaddate = new ArrayList<>();
        if (categories == null) {
            return;
        }

       BarGraphSeries series = new BarGraphSeries<>((new DataPoint[]{}));
        club.setOnClickListener(v -> {
            System.out.println("test 1");
            categories.add(club.getText().toString());
            catadd();
            System.out.println(categories.size() + "    size after add");
            System.out.println(a);
            series.appendData(new DataPoint(clubdate.get(a++), 1), true, 31);



        });
        eat = findViewById(R.id.eat);
        eat.setOnClickListener(v -> {

            categories.add(eat.getText().toString());
            catadd();
            series.appendData(new DataPoint(eatdate.get(b++), 2), true, 31);



        });
        festival = findViewById(R.id.festival);
        festival.setOnClickListener(v -> {

            categories.add(festival.getText().toString());
            catadd();

            series.appendData(new DataPoint(festivaldate.get(c++), 3), true, 31);




        });
        social = findViewById(R.id.socializing);

        social.setOnClickListener(v -> {


            categories.add(social.getText().toString());
            catadd();
            series.appendData(new DataPoint(socialdate.get(d++), 4), true, 31);




        });
        acad = findViewById(R.id.Academics);
        acad.setOnClickListener(v -> {

            categories.add(acad.getText().toString());
            catadd();
            series.appendData(new DataPoint(acaddate.get(e++), 5), true, 31);


        });
        graph.addSeries(series);


    }



    public void catadd() {

        EditText date = findViewById(R.id.date);
        if (date.getText().toString() == "") {
            return;
        }
        int  date1 = Integer.parseInt(date.getText().toString());
        System.out.println(date1);

        for (int i = 0; i < categories.size(); i++) {
            System.out.println(categories.get(i) + " CATEGORIESSSS");
            if (categories.get(i).equals(club.getText().toString())) {

                clubdate.add(date1);
                System.out.println(clubdate.get(i) + " Cause of crash");
            }
            if (categories.get(i).equals(eat.getText().toString())) {

                eatdate.add(date1);
            }
            if (categories.get(i).equals(festival.getText().toString())) {

                festivaldate.add(date1);
            } if (categories.get(i).equals(social.getText().toString())) {

                socialdate.add(date1);
            }
            if (categories.get(i).equals(acad.getText().toString())) {

                acaddate.add(date1);
            }

        }








    }



}




