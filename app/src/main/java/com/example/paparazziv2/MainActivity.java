package com.example.paparazziv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

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

    private ArrayList<values> xy;
    private PointsGraphSeries series;

    private int a, b, c, d, e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PointsGraphSeries series = new PointsGraphSeries<>();

        setContentView(R.layout.activity_main);

        categories = new ArrayList<>();
        xy = new ArrayList<>();

        Button enter = findViewById(R.id.enter);
        location = findViewById(R.id.location);
        club = findViewById(R.id.Clubbing);

        Intent intent = new Intent(this, GraphActivitynew.class);
        Intent intentMap = new Intent(this, MapsActivity.class);

        enter.setOnClickListener(view -> {
            intentMap.putExtra("Location", location.getText().toString());
            startActivity(intentMap);
        });
        init();



    }

    private void init(){
        //declare the xySeries Object
        series = new PointsGraphSeries<>();
        club = findViewById(R.id.Clubbing);

        Intent intent = new Intent(this, GraphActivitynew.class);


        club.setOnClickListener(v -> {
            intent.putExtra("Button", club.getText().toString());
            a++;
            intent.putExtra("a", a);
            xy.add(new values(0, a));
            init();


            // startActivity(intent);

        });
        eat = findViewById(R.id.eat);
        eat.setOnClickListener(v -> {

            intent.putExtra("Button", eat.getText().toString());
            b++;
            xy.add(new values(1, b));


            intent.putExtra("b", b);
            init();
            // series.appendData(new DataPoint(1, b), true, 31);


            //startActivity(intent);

        });

        festival = findViewById(R.id.festival);
        festival.setOnClickListener(v -> {
            intent.putExtra("Button", festival.getText().toString());
            c++;
            xy.add(new values(2, c));


            intent.putExtra("c", c);
            init();
            // series.appendData(new DataPoint(2, c), true, 31);

            //startActivity(intent);

        });

        social = findViewById(R.id.socializing);

        social.setOnClickListener(v -> {

            intent.putExtra("Button", social.getText().toString());
            d++;
            xy.add(new values(3, d));


            //series.appendData(new DataPoint(3, d), true, 31);

            intent.putExtra("d", d);
            init();
            //startActivity(intent);

        });
        acad = findViewById(R.id.Academics);
        acad.setOnClickListener(v -> {

            e++;
            intent.putExtra("Button", acad.getText().toString());

            intent.putExtra("e", e);
            xy.add(new values(4, e));
            init();



            //  series.appendData(new DataPoint(4, e), true, 31);


            //startActivity(intent);


        });
        if (xy == null) {
            return;
        }
        xy = sort(xy);

        for (int i = 0; i < xy.size(); i++) {

            double x = xy.get(i).getX();
            double y = xy.get(i).getY();
            series.appendData(new DataPoint(x, y), true, 1000);
        }
        //set some properties
        series.setShape(PointsGraphSeries.Shape.RECTANGLE);
        series.setColor(Color.BLUE);
        series.setSize(20f);
        GraphView graphView = findViewById(R.id.graphnew);


        //set Scrollable and Scaleable
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);

        //set manual x bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(150);
        graphView.getViewport().setMinY(-150);

        //set manual y bounds
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(150);
        graphView.getViewport().setMinX(-150);

        graphView.addSeries(series);
        //String s = i.getStringExtra("Series");
        //BarGraphSeries ob = g.fromJson(s, BarGraphSeries.class);

        //graphView.addSeries(ob);








    }




    private ArrayList<values> sort(ArrayList<values> array) {


        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(), 2))));
        int m = array.size() - 1;
        int count = 0;


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            try {
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    // Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                    //Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                //Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                //     e.getMessage());
                break;
            }
        }
        return array;
    }
}
















