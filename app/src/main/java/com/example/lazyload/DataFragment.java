package com.example.lazyload;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataFragment extends Fragment implements SearchView.OnQueryTextListener {
    int limit = 500;
    int offset = 0;
    int currentItems, totalItems, scrollOutItems;
    private SOService mService;
    AdapterShowDetail adapterShowDetail;
    RecyclerView rvDetail;
    ProgressBar progressBar;
    ArrayList<DatailInfo> details;
    Boolean isLoading = false;
    private ResultViewModel viewModel;
    boolean checkSearch = true;
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
        mService = ApiUtils.getSOService();

        adapterShowDetail = new AdapterShowDetail(getContext());
        rvDetail = rootView.findViewById(R.id.recycler_detail);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDetail.setLayoutManager(layoutManager);
        rvDetail.setHasFixedSize(true);
        rvDetail.setAdapter(adapterShowDetail);




        rvDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isLoading = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if (isLoading && (currentItems + scrollOutItems == totalItems) && checkSearch) {
                    isLoading = false;
                    offset = offset + 500;
                    progressBar.setVisibility(View.VISIBLE);

                    perform();


                }

            }
        });
        loadData();


        viewModel = ViewModelProviders.of(getActivity()).get(ResultViewModel.class);


        return  rootView;

    }


    private void perform() {
        mService.getAnswers(limit, offset).enqueue(new Callback<List<DatailInfo>>() {
            @Override
            public void onResponse(Call<List<DatailInfo>> call, Response<List<DatailInfo>> response) {
                ArrayList<DatailInfo> datas = new ArrayList();
                datas.addAll(response.body());
                adapterShowDetail.addData(datas);
                viewModel.dataSave(datas);
                adapterShowDetail.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<DatailInfo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void loadData() {

        mService.getAnswers(limit, offset).enqueue(new Callback<List<DatailInfo>>() {
            @Override
            public void onResponse(Call<List<DatailInfo>> call, Response<List<DatailInfo>> response) {

                details.addAll(response.body());
                viewModel.dataSave(details);
                viewModel.getDataSave().observe((LifecycleOwner) getContext(), new Observer<ArrayList<DatailInfo>>() {
                    @Override
                    public void onChanged( ArrayList<DatailInfo> datailInfos) {
                        progressBar.setVisibility(View.INVISIBLE);
                        adapterShowDetail.setData(datailInfos);

                    }


                });
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<DatailInfo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuitemsearch = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuitemsearch.getActionView();

        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(final String s) {
        final ArrayList<DatailInfo> newList = new ArrayList();
        adapterShowDetail.update(newList);
        checkSearch = false;

        progressBar.setVisibility(View.VISIBLE);
        mService.findItem().enqueue(new Callback<List<DatailInfo>>() {

            @Override

            public void onResponse(Call<List<DatailInfo>> call, Response<List<DatailInfo>> response) {
                for (DatailInfo data : response.body()) {

                    if (data.getId().equals(s)) {

                        newList.add(data);


                    }
                }
                adapterShowDetail.addData(newList);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<DatailInfo>> call, Throwable t) {

            }
        });


        return false;
    }

    @Override
    public boolean onQueryTextChange(final String s) {

        if (s.length() == 0) {
            adapterShowDetail.update(details);
            checkSearch = true;
        }

        return true;

    }




}
