package com.example.paparazziv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//1.
//2. Add polylines ( I know how to do just need to save the states
//3. After a day passes the lines get drawn automatically. So after each day the lines drawn are shown.

public class MainActivity extends AppCompatActivity {
    // private EditText location;
    private Button club, eat, festival, social, acad;
    private List<String> categories;

    private ArrayList<values> xy;
    private PointsGraphSeries series;

    private int a, b, c, d, e;
    private PlacesClient client;
    private List<LatLng> locations = new ArrayList<>();

    private final int PERMISSION_FINE_LOCATION_ACCESS = 1;
    private Button markCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PointsGraphSeries series = new PointsGraphSeries<>();

        Intent intentMap = new Intent(this, MapsActivity.class);

        setContentView(R.layout.activity_main);

        markCurrentLocation = findViewById(R.id.markCurrent);

        // Requesting location access
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSION_FINE_LOCATION_ACCESS);
        }

        String key = "AIzaSyC1__M1ff-99MrCEfi0B0CB6PByZi3AJOg";
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), key);
        }
        client = Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng location = place.getLatLng();
                double lat = location.latitude;
                double longit = location.longitude;
                intentMap.putExtra("latitude", lat);
                intentMap.putExtra("longitude", longit);
                locations.add(location);
                Gson gson = new Gson();
                String locater = gson.toJson(locations);
                intentMap.putExtra("locations", locater);


                startActivity(intentMap);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e("Error in status", " Error in getting status");

            }
        });

        categories = new ArrayList<>();
        xy = new ArrayList<>();

        club = findViewById(R.id.Clubbing);

        init();
    }

    private void init(){
        //declare the xySeries Object
        series = new PointsGraphSeries<>();

        club.setOnClickListener(v -> {
            a++;
            xy.add(new values(1, a));
            init();

        });
        eat = findViewById(R.id.eat);
        eat.setOnClickListener(v -> {
            b++;
            xy.add(new values(2, b));

            init();
            // series.appendData(new DataPoint(1, b), true, 31);
            //startActivity(intent);
        });

        festival = findViewById(R.id.festival);
        festival.setOnClickListener(v -> {
            c++;
            xy.add(new values(3, c));
            init();
            // series.appendData(new DataPoint(2, c), true, 31);
            //startActivity(intent);
        });

        social = findViewById(R.id.socializing);

        social.setOnClickListener(v -> {
            d++;
            xy.add(new values(4, d));


            init();
            //startActivity(intent);

        });
        acad = findViewById(R.id.Academics);
        acad.setOnClickListener(v -> {

            e++;
            xy.add(new values(5, e));
            init();


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
        series.setSize(20f);
        GraphView graphView = findViewById(R.id.graphnew);


        //set Scrollable and Scaleable
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);

        //set manual x bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(50);
        graphView.getViewport().setMinY(0);

        //set manual y bounds
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(6);
        graphView.getViewport().setMinX(0);

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_FINE_LOCATION_ACCESS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    // make a toast mesasge
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    // Hide the mark current button.
                    // Make a toast message.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
















