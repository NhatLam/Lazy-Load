package com.example.lazyload;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;



public class ResultViewModel extends ViewModel {
    LiveData<PagedList<DetailInfo>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, DetailInfo>> liveDataSource;


    public ResultViewModel() {

        DataSourceFactory itemDataSourceFactory = new DataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setPageSize(MyDataSource.PAGE_SIZE).build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)).build();
    }



}

