package com.example.paparazziv2;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /** The map to be manipulated. */
    private GoogleMap mMap;

    private LatLng position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        }


    MarkerOptions options;
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        centerMap(mMap);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("latitude", 0.0);
        double longit = intent.getDoubleExtra("longitude", 0.0);
        position = new LatLng(lat, longit);


//        RequestQueue requestQueue = Volley.newRequestQueue(this);

//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://cs125-cloud.cs.illinois.edu/Fall2019-MP/presets/",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        JsonParser ob = new JsonParser();
//                        JsonElement element = ob.parse(response);
//                        JsonObject jsonObject = element.getAsJsonObject();
//
//                        JsonArray presets = jsonObject.get("presets").getAsJsonArray();
//                        JsonElement campus = presets.get(1);
//                        JsonArray loc = campus.getAsJsonObject().get("targets").getAsJsonArray();
//
//                        for (JsonElement i : loc) {
//
//                            if (searchKey.equals(i.getAsJsonObject().get("note").getAsString())) {
//                                System.out.println(searchKey + "     Location found");
//                                position = new LatLng(i.getAsJsonObject().get("latitude").getAsDouble(),
//                                        i.getAsJsonObject().get("longitude").getAsDouble());
//                                options = new MarkerOptions().position(position);
//                                mMap.addMarker(options);
//
//                                mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
//                            }
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(stringRequest);

        options = new MarkerOptions().position(position);
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    private void centerMap(final GoogleMap map) {
        // Bounds of campustown and some surroundings
        final double swLatitude = 40.098331;
        final double swLongitude = -88.246065;
        final double neLatitude = 40.116601;
        final double neLongitude = -88.213077;

        // Get the window dimensions (for the width)
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);

        // Convert 300dp (height of map control) to pixels
        final int mapHeightDp = 300;
        float heightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mapHeightDp,
                getResources().getDisplayMetrics());

        // Submit the camera update
        final int paddingPx = 10;
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(
                new LatLng(swLatitude, swLongitude),
                new LatLng(neLatitude, neLongitude)), windowSize.x, (int) heightPx, paddingPx));
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("latitude", position.latitude  );
        outState.putDouble("longitude", position.longitude);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
