package com.example.lazyload;


import android.arch.lifecycle.ViewModelProviders;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsFragment extends Fragment   {
    GoogleMap map;
    MapView mMapView;
    ResultViewModel viewModel;
    public MapsFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = rootView.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        viewModel = ViewModelProviders.of(getActivity()).get(ResultViewModel.class);
        viewModel.itemPagedList.observe(getActivity(),  items -> {
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    for (int i = 0; i < items.size(); i++) {
                        map.addMarker(new MarkerOptions().title(items.get(i).getId())
                                .snippet(items.get(i).getDyadName())
                                .position(new LatLng(Double.parseDouble(items.get(i).getLatitude()), Double.parseDouble(items.get(i).getLongitude()))));
                    }
                }
            });





        });


        return rootView;
    }


}

