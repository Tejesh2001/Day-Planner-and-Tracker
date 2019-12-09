package com.example.paparazziv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//1.
//2. Add polylines ( I know how to do just need to save the states
//3. After a day passes the lines get drawn automatically. So after each day the lines drawn are shown.

public class MainActivity extends AppCompatActivity {
    // private EditText location;
    // private Button club, eat, festival, social, acad;
    private final String LOG_TAG = "MainActivity";

    private PlacesClient client;
    private List<LatLng> locations = new ArrayList<>();
    private List<Position> positions = new ArrayList<>();

    private final int PERMISSION_FINE_LOCATION_ACCESS = 1;
    private boolean hasLocationAccess;
    private int markerSelection = -1;
    private final int MARKER_SELECTION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PointsGraphSeries series = new PointsGraphSeries<>();

        Intent intentMap = new Intent(this, MapsActivity.class);

        setContentView(R.layout.activity_main);


        Button club = findViewById(R.id.Clubbing);
        club.setOnClickListener(unused -> {
            if (markerSelection == -1) {
                Toast.makeText(getApplicationContext(), "Select a marker first.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Position selected = positions.get(markerSelection);
                selected.incrementClub();
                renderGraph();
            }
        });
        Button eat = findViewById(R.id.eat);
        eat.setOnClickListener(unused -> {
            if (markerSelection == -1) {
                Toast.makeText(getApplicationContext(), "Select a marker first.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Position selected = positions.get(markerSelection);
                selected.incrementEat();
                renderGraph();
            }
        });
        Button festival = findViewById(R.id.festival);
        festival.setOnClickListener(unused -> {
            if (markerSelection == -1) {
                Toast.makeText(getApplicationContext(), "Select a marker first.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Position selected = positions.get(markerSelection);
                selected.incrementFestival();
                renderGraph();
            }
        });
        Button social = findViewById(R.id.socializing);
        social.setOnClickListener(unused -> {
            if (markerSelection == -1) {
                Toast.makeText(getApplicationContext(), "Select a marker first.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Position selected = positions.get(markerSelection);
                selected.incrementSocial();
                renderGraph();
            }
        });
        Button acad = findViewById(R.id.Academics);
        acad.setOnClickListener(unused -> {
            if (markerSelection == -1) {
                Toast.makeText(getApplicationContext(), "Select a marker first.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Position selected = positions.get(markerSelection);
                selected.incrementAcademic();
                renderGraph();
            }
        });

        //init();
        //renderGraph();
    }

    private void renderGraph() {
        Position selected = positions.get(markerSelection);
        GraphView graph = findViewById(R.id.graphnew);
        graph.removeAllSeries();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, selected.getClubCount()),
                new DataPoint(2, selected.getEatCount()),
                new DataPoint(3, selected.getFestivalCount()),
                new DataPoint(4, selected.getSocializeCount()),
                new DataPoint(5, selected.getAcademicCount())
        });
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
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        //set manual x bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(50);
        graph.getViewport().setMinY(0);

        //set manual y bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(6);
        graph.getViewport().setMinX(0);
    }

//    private void init(){
//        series = new BarGraphSeries<>();
//        Button club = findViewById(R.id.Clubbing);
//        club.setOnClickListener(v -> {
//            a++;
//            xy.add(new values(1, a));
//            init();
//
//        });
//
//        Button eat = findViewById(R.id.eat);
//        eat.setOnClickListener(v -> {
//            b++;
//            xy.add(new values(2, b));
//
//            init();
//            // series.appendData(new DataPoint(1, b), true, 31);
//            //startActivity(intent);
//        });
//
//        Button festival = findViewById(R.id.festival);
//        festival.setOnClickListener(v -> {
//            c++;
//            xy.add(new values(3, c));
//            init();
//            // series.appendData(new DataPoint(2, c), true, 31);
//            //startActivity(intent);
//        });
//
//        Button social = findViewById(R.id.socializing);
//        social.setOnClickListener(v -> {
//            d++;
//            xy.add(new values(4, d));
//            init();
//            //startActivity(intent);
//
//        });
//
//        Button acad = findViewById(R.id.Academics);
//        acad.setOnClickListener(v -> {
//            e++;
//            xy.add(new values(5, e));
//            init();
//        });
//
//        if (xy == null) {
//            return;
//        }
//        xy = sort(xy);
//        for (int i = 0; i < xy.size(); i++) {
//
//            double x = xy.get(i).getX();
//            double y = xy.get(i).getY();
//            series.appendData(new DataPoint(x, y), true, 1000);
//        }
//        //set some properties
//        series.setShape(PointsGraphSeries.Shape.RECTANGLE);
//        series.setSize(20f);
//        GraphView graphView = findViewById(R.id.graphnew);
//
//
//        //set Scrollable and Scaleable
//        graphView.getViewport().setScalable(true);
//        graphView.getViewport().setScalableY(true);
//        graphView.getViewport().setScrollable(true);
//        graphView.getViewport().setScrollableY(true);
//
//        //set manual x bounds
//        graphView.getViewport().setYAxisBoundsManual(true);
//        graphView.getViewport().setMaxY(50);
//        graphView.getViewport().setMinY(0);
//
//        //set manual y bounds
//        graphView.getViewport().setXAxisBoundsManual(true);
//        graphView.getViewport().setMaxX(6);
//        graphView.getViewport().setMinX(0);
//
//        graphView.addSeries(series);
//        //String s = i.getStringExtra("Series");
//        //BarGraphSeries ob = g.fromJson(s, BarGraphSeries.class);
//
//        //graphView.addSeries(ob);
//    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_FINE_LOCATION_ACCESS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Mark current location function has been disabled",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MARKER_SELECTION_REQUEST) {
            if (resultCode == RESULT_OK) {
                markerSelection = data.getIntExtra("selection", -1);
                if (markerSelection != -1) {
                    renderGraph();
                }
            }
        }
    }


}
