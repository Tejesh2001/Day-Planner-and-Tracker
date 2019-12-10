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
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /** The map to be manipulated. */
    private GoogleMap mMap;

    private LatLng currentPosition = null;

    List<LatLng> locatorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        String locaters = intent.getStringExtra("locations");
        Gson gson = new Gson();
        Type type = new TypeToken<List<LatLng>>(){}.getType();
        locatorsList = gson.fromJson(locaters, type);
        double currentLat = intent.getDoubleExtra("currentLat", -600);
        double currentLng = intent.getDoubleExtra("currentLng", -600);
        if (currentLat != -600 && currentLng != -600) {
            currentPosition = new LatLng(currentLat, currentLng);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

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

        mMap.setOnMarkerClickListener(marker -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selection", (Integer) marker.getTag());
            setResult(RESULT_OK, resultIntent);
            return false;
        });

        // centerMap(mMap);

        int size = locatorsList.size();
        for (int i = 0; i < size; i++) {
            MarkerOptions options = new MarkerOptions().position(locatorsList.get(i));
            Marker marker = mMap.addMarker(options);
            marker.setTag(i);
            if (i < size - 1) {
                mMap.addPolyline(new PolylineOptions().add(locatorsList.get(i),
                        locatorsList.get(i + 1)));
            }
            if (i == size - 1) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(locatorsList.get(i)));
            }

        }

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

        if (currentPosition != null) {
            MarkerOptions currentOption = new MarkerOptions().position(currentPosition);
            mMap.addMarker(currentOption);
        }
    }

//    private void centerMap(final GoogleMap map) {
//        // Bounds of campustown and some surroundings
//        final double swLatitude = 40.098331;
//        final double swLongitude = -88.246065;
//        final double neLatitude = 40.116601;
//        final double neLongitude = -88.213077;
//
//        // Get the window dimensions (for the width)
//        Point windowSize = new Point();
//        getWindowManager().getDefaultDisplay().getSize(windowSize);
//
//        // Convert 300dp (height of map control) to pixels
//        final int mapHeightDp = 300;
//        float heightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mapHeightDp,
//                getResources().getDisplayMetrics());
//
//        // Submit the camera update
//        final int paddingPx = 10;
//        map.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(
//                new LatLng(swLatitude, swLongitude),
//                new LatLng(neLatitude, neLongitude)), windowSize.x, (int) heightPx, paddingPx));
//    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putDouble("latitude", position.latitude);
        // outState.putDouble("longitude", position.longitude);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
