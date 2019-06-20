package com.example.lazyload;


import android.arch.lifecycle.ViewModelProviders;

import android.arch.paging.PagedList;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Person;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;


public class MapsFragment extends Fragment {
    GoogleMap map;
    MapView mMapView;
    ResultViewModel viewModel;
    ClusterManager<MyCluster> mClusterManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = rootView.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        viewModel = ViewModelProviders.of(getActivity()).get(ResultViewModel.class);


        return rootView;
    }

    public void setMapview(PagedList<DetailInfo> items) {

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                mClusterManager = new ClusterManager(getActivity(), map);
                map.setOnCameraIdleListener(mClusterManager);
                map.setOnMarkerClickListener(mClusterManager);
                for (int i = 0; i < items.size(); i++) {

                    MyCluster offsetItem = new MyCluster(Double.parseDouble(items.get(i).getLatitude()), Double.parseDouble((items.get(i).getLongitude())), items.get(i).getId(), items.get(i).getDyadName());
                    mClusterManager.addItem(offsetItem);
                    mClusterManager.cluster();

                }


            }
        });


    }

    public void reloadMapview() {
        mMapView.invalidate();

        viewModel.itemPagedList.observe(getActivity(), items -> {
            if (!items.isEmpty()) {
                setMapview(items);
            }
        });
    }


}

