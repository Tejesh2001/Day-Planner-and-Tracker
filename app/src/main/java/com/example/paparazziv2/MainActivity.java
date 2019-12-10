package com.example.paparazziv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
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

public class MainActivity extends AppCompatActivity {
    // private EditText location;
    // private Button club, eat, festival, social, acad;
    // private final String LOG_TAG = "MainActivity";

    private PlacesClient client;
    private List<LatLng> locations = new ArrayList<>();
    private List<Position> positions = new ArrayList<>();
    private double currentLat;
    private double currentLng;

    private final int PERMISSION_FINE_LOCATION_ACCESS = 1;
    private boolean hasLocationAccess;
    private int markerSelection = -1;
    private final int MARKER_SELECTION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PointsGraphSeries series = new PointsGraphSeries<>();
        Gson gson = new Gson();
        Intent intentMap = new Intent(this, MapsActivity.class);

        setContentView(R.layout.activity_main);


        hasLocationAccess = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
        // Requesting location access
        if (!hasLocationAccess) {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_FINE_LOCATION_ACCESS);
        }

        locationGetter();

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

                intentMap.putExtra("Latitude", location.latitude);
                intentMap.putExtra("Longitude", location.longitude);


                locations.add(location);
                positions.add(new Position(location));
                String locater = gson.toJson(locations);
                intentMap.putExtra("locations", locater);
                if (hasLocationAccess) {
                    intentMap.putExtra("currentLat", currentLat);
                    intentMap.putExtra("currentLng", currentLng);
                }

                startActivityForResult(intentMap, MARKER_SELECTION_REQUEST);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e("Error in status", " Error in getting status");
            }
        });

        Button viewMap = findViewById(R.id.viewMap);
        viewMap.setOnClickListener(unused -> {
            String locater = gson.toJson(locations);
            intentMap.putExtra("locations", locater);
            startActivityForResult(intentMap, MARKER_SELECTION_REQUEST);
        });

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

    /**
     * Used to acquire the user's current location.
     */
    private void locationGetter() {
        CurrentLocationGetter.LocationResult locationResult = new CurrentLocationGetter.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                //Got the location!
                currentLat = location.getLatitude();
                currentLng = location.getLongitude();
            }
        };
        CurrentLocationGetter myLocation = new CurrentLocationGetter();
        myLocation.getLocation(this, locationResult);
    }

}
