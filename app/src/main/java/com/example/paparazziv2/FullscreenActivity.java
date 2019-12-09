package com.example.paparazziv2;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        Button map = findViewById(R.id.mapbutton);
        Button graph = findViewById(R.id.Graph);
        map.setOnClickListener(v ->
                startActivity(new Intent(this, Search.class)));
        graph.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));


        // Set up the user interaction to manually show or hide the system UI.
    }
}
