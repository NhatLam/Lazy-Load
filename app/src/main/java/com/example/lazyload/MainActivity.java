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
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    int limit=10;
    int offset=0;
    private SOService mService;
    AdapterShowDetail adapterShowDetail;
    RecyclerView rvDetail;
    ProgressBar progressBar;
    ArrayList<DatailInfo> details;
    Boolean isLoading = true;
    private ResultViewModel viewModel;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        details= new ArrayList();

        mService = ApiUtils.getSOService();

        progressBar=findViewById(R.id.progess);
        progressBar.setVisibility(View.VISIBLE);
        rvDetail = findViewById(R.id.recycler_detail);
        rvDetail.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDetail.setLayoutManager(layoutManager);
        loadData();

        rvDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy>0)
                {
                    if(isLoading)
                    {
                           offset=offset+10;
                           isLoading=false;

                    }
                    if(!isLoading ){
                        perform();
                        isLoading=true;

                    }
                }
            }
        });
        viewModel = ViewModelProviders.of(this).get(ResultViewModel.class);

        viewModel.getDataSave().observe(this, new Observer<ArrayList<DatailInfo>>() {
            @Override
            public void onChanged(@Nullable ArrayList<DatailInfo> datailInfos) {
                adapterShowDetail=new AdapterShowDetail(datailInfos,MainActivity.this);
                rvDetail.setAdapter(adapterShowDetail);

            }

        });
        }
    private void perform() {
        mService.getAnswers(limit,offset).enqueue(new Callback<List<DatailInfo>>() {
            @Override
            public void onResponse(Call<List<DatailInfo>> call, Response<List<DatailInfo>> response) {
                ArrayList<DatailInfo> datas=new ArrayList();
                datas.addAll(response.body());
                adapterShowDetail.addData(datas);
                viewModel.dataSave(adapterShowDetail.getDsDetail());

                progressBar.setVisibility(View.GONE);


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
                adapterShowDetail=new AdapterShowDetail(details,MainActivity.this);
                rvDetail.setAdapter(adapterShowDetail);
                progressBar.setVisibility(View.GONE);
                viewModel.dataSave(adapterShowDetail.getDsDetail());

            }

            @Override
            public void onFailure(Call<List<DatailInfo>> call, Throwable t) {

            }
        });
    }



}
