package com.example.paparazziv2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class Launch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button map = findViewById(R.id.mapbutton);
        Button graph = findViewById(R.id.Graph);
        map.setOnClickListener(v ->
                startActivity(new Intent(this, Search.class)));
        graph.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));





    }

}