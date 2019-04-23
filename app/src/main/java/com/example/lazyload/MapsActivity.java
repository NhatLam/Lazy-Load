package com.example.lazyload;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigation;

    ArrayList<DatailInfo> list;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        bottomNavigation = findViewById(R.id.bottom_navi);

        bottomNavigation.setOnNavigationItemSelectedListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent recevice = getIntent();
        list = (ArrayList<DatailInfo>) recevice.getSerializableExtra("list");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_data:
                Intent data = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(data);
                break;
            case R.id.action_location:
                break;

        }
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        for (int i = 0; i < list.size(); i++) {
            map.addMarker(new MarkerOptions().title(list.get(i).getId())
                    .snippet(list.get(i).getDyadName())
                    .position(new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()))));
        }

    }


}
