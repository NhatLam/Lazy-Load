package com.example.lazyload;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    int limit=10;
    int offset=0;
    int currentItems, totalItems, scrollOutItems;
    private SOService mService;
    AdapterShowDetail adapterShowDetail;
    RecyclerView rvDetail;
    ProgressBar progressBar;
    ArrayList<DatailInfo> details;
    Boolean isLoading = false;
    private  ResultViewModel viewModel;

    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        details= new ArrayList();
        progressBar=findViewById(R.id.progess);
        progressBar.setVisibility(View.VISIBLE);
        mService = ApiUtils.getSOService();

        adapterShowDetail=new AdapterShowDetail(this);
        rvDetail = findViewById(R.id.recycler_detail);
        rvDetail.setHasFixedSize(true);
        rvDetail.setAdapter(adapterShowDetail);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDetail.setLayoutManager(layoutManager);

        rvDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isLoading = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if(isLoading && (currentItems + scrollOutItems == totalItems))
                {
                    isLoading = false;
                    offset=offset+10;
                    progressBar.setVisibility(View.VISIBLE);

                    perform();
                }

            }
        });
        loadData();


        viewModel=ViewModelProviders.of(this).get(ResultViewModel.class);


    }

    private void perform() {
        mService.getAnswers(limit,offset).enqueue(new Callback<List<DatailInfo>>() {
            @Override
            public void onResponse(Call<List<DatailInfo>> call, Response<List<DatailInfo>> response) {
                ArrayList<DatailInfo> datas=new ArrayList();
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

    private void loadData() {

        mService.getAnswers(limit,offset).enqueue(new Callback<List<DatailInfo>>() {
            @Override
            public void onResponse(Call<List<DatailInfo>> call, Response<List<DatailInfo>> response) {

                details.addAll(response.body());
                viewModel.dataSave(details);
                viewModel.getDataSave().observe(MainActivity.this, new Observer<ArrayList<DatailInfo>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<DatailInfo> datailInfos) {
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



}