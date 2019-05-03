package com.example.lazyload;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;


public class DataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, DetailInfo>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, DetailInfo>  create() {
        MyDataSource itemDataSource = new MyDataSource();
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, DetailInfo>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }



}

