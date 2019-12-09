package com.example.paparazziv2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Launch extends AppCompatActivity {


    private final int PERMISSION_FINE_LOCATION_ACCESS = 1;
    private boolean hasLocationAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        hasLocationAccess = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);

        // Requesting location access
        if (!hasLocationAccess) {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(Launch.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_FINE_LOCATION_ACCESS);
        }



        Button map = findViewById(R.id.mapbutton);
        Button graph = findViewById(R.id.Graph);
        map.setOnClickListener(v ->
                startActivity(new Intent(this, Search.class)));
        graph.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));





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


}
