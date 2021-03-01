package com.manjurulhoque.mynearbyplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.manjurulhoque.mynearbyplaces.activity.PlaceOnMapActivity;


public class old_mission_start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_mission_start);

        Intent intent = new Intent(old_mission_start.this, mission_complete.class);
        startActivity(intent);

    }

}
