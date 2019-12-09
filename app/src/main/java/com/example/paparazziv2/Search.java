package com.example.paparazziv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends AppCompatActivity {
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
        setContentView(R.layout.activity_search);

        Intent intentMap = new Intent(this, MapsActivity.class);

        hasLocationAccess = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);

        // Requesting location access
        if (!hasLocationAccess) {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(Search.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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

                intentMap.putExtra("Latitude", location.latitude);
                intentMap.putExtra("Longitude", location.longitude);


                locations.add(location);
                positions.add(new Position(location));
                Gson gson = new Gson();
                String locater = gson.toJson(locations);
                intentMap.putExtra("locations", locater);
                if (hasLocationAccess) {
                    GpsLocationTracker tracker = new GpsLocationTracker(getApplicationContext());
                    double currentLat = tracker.getLatitude();
                    double currentLng = tracker.getLongitude();
                    intentMap.putExtra("currentLat", currentLat);
                    intentMap.putExtra("currentLng", currentLng);
                }

                startActivity(intentMap);



            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e("Error in status", " Error in getting status");

            }
        });




    }
}
