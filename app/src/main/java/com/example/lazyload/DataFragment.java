package com.example.lazyload;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;



public  class DataFragment extends Fragment {

    AdapterShowDetail adapterShowDetail;
    RecyclerView rvDetail;
    ProgressBar progressBar;
    ArrayList<DetailInfo> details;
    ResultViewModel viewModel;
    LinearLayoutManager layoutManager;

    public DataFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        details = new ArrayList();

        progressBar = rootView.findViewById(R.id.progess);
        progressBar.setVisibility(View.VISIBLE);


        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        rvDetail = rootView.findViewById(R.id.recycler_detail);
        rvDetail.setLayoutManager(layoutManager);
        rvDetail.setHasFixedSize(true);


        //setHasOptionsMenu(true);

        viewModel = ViewModelProviders.of(getActivity()).get(ResultViewModel.class);
        adapterShowDetail = new AdapterShowDetail(getContext());

        viewModel.itemPagedList.observe(getActivity(), items -> {
            adapterShowDetail.submitList(items);
            progressBar.setVisibility(View.GONE);

        });

        rvDetail.setAdapter(adapterShowDetail);


        return rootView;

    }



}
