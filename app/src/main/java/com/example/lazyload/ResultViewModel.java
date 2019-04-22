package com.example.lazyload;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import java.util.ArrayList;

public class ResultViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<DatailInfo>> dataSave = new MutableLiveData();
    ArrayList<DatailInfo> datas=new ArrayList<>();
    public ResultViewModel(@NonNull Application application) {
        super(application);
        datas=new  ArrayList<>();

        dataSave.postValue(datas);
    }
    public void dataSave(ArrayList<DatailInfo> save) {
        datas.addAll(save);
    }



    public MutableLiveData<ArrayList<DatailInfo>> getDataSave() {
        return dataSave;
    }

}

