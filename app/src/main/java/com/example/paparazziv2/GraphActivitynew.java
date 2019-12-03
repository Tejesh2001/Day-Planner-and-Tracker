package com.example.paparazziv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class GraphActivitynew extends AppCompatActivity {


    private Button club, eat, festival, social, acad;
    private List<String> categories;
    private List<Integer> clubdate, eatdate, socialdate, acaddate, festivaldate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_activitynew);
        GraphView graph = findViewById(R.id.graphnew);
        String button = getIntent().getStringExtra("Button");
        int a = getIntent().getIntExtra("a", 0);
        int b = getIntent().getIntExtra("b", 0);
        int c = getIntent().getIntExtra("c", 0);
        int d = getIntent().getIntExtra("d", 0);
        int e = getIntent().getIntExtra("e", 0);
        categories = new ArrayList<>();





        BarGraphSeries series = new BarGraphSeries<>((new DataPoint[]{}));


        if (button.equals("Clubbing/Bar hopping")) {
            System.out.println(button + "  button new");
            //lmao

            categories.add(button);

            series.appendData(new DataPoint(0, a), true, 31);

        }


        if (button.equals("Eating out")) {

            categories.add(button);
            series.appendData(new DataPoint(1, b ), true, 31);


        }
        if (button.equals("Festival")) {
            categories.add(button);


            series.appendData(new DataPoint(2, c ), true, 31) ;

        }

        if (button.equals("Socializing")) {


            categories.add(button);

            series.appendData(new DataPoint(3, d ), true, 31);

        }
        if (button.equals("Academics")) {


            categories.add(button);


            series.appendData(new DataPoint(4, e ), true, 31);


        }


        graph.addSeries(series);


        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });


        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}


