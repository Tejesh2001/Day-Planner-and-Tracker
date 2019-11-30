package com.example.paparazziv2;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.Series;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Graph extends AppCompatActivity {
    private List<Integer> clubdate, eatdate, socialdate, acaddate, festivaldate;

    private int a, b, c, d, e;
    private List<String> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_graph);
        //GraphView graph = findViewById(R.id.graph);
        GraphView graph = findViewById(R.id.graph);
        String button = getIntent().getStringExtra("Button");
        clubdate = new ArrayList<>();
        eatdate = new ArrayList<>();
        festivaldate = new ArrayList<>();
        socialdate = new ArrayList<>();
        acaddate = new ArrayList<>();
        categories = new ArrayList<>();


        BarGraphSeries series = new BarGraphSeries<>((new DataPoint[]{}));

        if (button.equals("Clubbing/Bar hopping")) {
            System.out.println(button + "  button new");

            categories.add(button);

            series.appendData(new DataPoint(0, a++), true, 31);

        }


        if (button.equals("Eating out")) {

            categories.add(button);
            series.appendData(new DataPoint(1, b++), true, 31);


        }
        if (button.equals("Festival")) {
            categories.add(button);


            series.appendData(new DataPoint(2, c++), true, 31);

        }

        if (button.equals("Socializing")) {


            categories.add(button);

            series.appendData(new DataPoint(3, d++), true, 31);

        }
        if (button.equals("Academics")) {


            categories.add(button);


            series.appendData(new DataPoint(4, e++), true, 31);


        }


        graph.addSeries(series);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);


    }


    /*public void catadd() {

        EditText date = findViewById(R.id.date);
        if (date.getText().toString().equals("")) {
            return;
        }
        int date1 = Integer.parseInt(date.getText().toString());
        System.out.println(date1);

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).equals("Clubbing/Bar Hopping")) {

                clubdate.add(date1);
            }
            if (categories.get(i).equals("Eating Out")) {

                eatdate.add(date1);
            }
            if (categories.get(i).equals("Festival")) {

                festivaldate.add(date1);
            }
            if (categories.get(i).equals("Socializing")) {

                socialdate.add(date1);
            }
            if (categories.get(i).equals("Academics")) {

                acaddate.add(date1);
            }

        }


    }*/
}


