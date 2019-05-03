package com.example.lazyload;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDataSource extends PageKeyedDataSource<Integer, DetailInfo> {

    public static int   PAGE_SIZE=15;

    private static final int  FIRST_PAGE = 0;
    SOService service;
    public MyDataSource(){
        service = ApiUtils.getSOService();

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, DetailInfo> callback) {

        service.getAnswers(PAGE_SIZE, FIRST_PAGE).enqueue(new Callback<List<DetailInfo>>() {
            @Override
            public void onResponse(Call<List<DetailInfo>> call, Response<List<DetailInfo>> response) {
                if (response.body() != null) {

                    callback.onResult(response.body(), null, FIRST_PAGE + 15);
                }
            }

            @Override
            public void onFailure(Call<List<DetailInfo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, DetailInfo> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, DetailInfo> callback) {

        service.getAnswers(PAGE_SIZE, params.key).enqueue(new Callback<List<DetailInfo>>() {
            @Override
            public void onResponse(Call<List<DetailInfo>> call, Response<List<DetailInfo>> response) {
                if (response.body() != null) {
                    callback.onResult(response.body(), params.key + 15);
                }
            }

            @Override
            public void onFailure(Call<List<DetailInfo>> call, Throwable t) {

            }
        });
    }

}
