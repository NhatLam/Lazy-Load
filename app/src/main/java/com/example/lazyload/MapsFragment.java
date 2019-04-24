package com.example.lazyload;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;


public class MapsFragment extends Fragment {
    ArrayList<DatailInfo> list;
    GoogleMap map;
    MapView mMapView;
    ResultViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = rootView.findViewById(R.id.map);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        viewModel = ViewModelProviders.of(getActivity()).get(ResultViewModel.class);


        viewModel.getDataSave().observe(getActivity(), new Observer<ArrayList<DatailInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<DatailInfo> datailInfos) {
                list = datailInfos;
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        for (int i = 0; i < list.size(); i++) {
                            map.addMarker(new MarkerOptions().title(list.get(i).getId())
                                    .snippet(list.get(i).getDyadName())
                                    .position(new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()))));
                        }
                    }
                });
            }


        });
    }
}

