package com.example.lazyload;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class ResultViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<DatailInfo>> dataSave = new MutableLiveData();

    public ResultViewModel(@NonNull Application application) {
        super(application);


        dataSave.postValue(null);

    }
    public void dataSave(ArrayList<DatailInfo> save) {
        dataSave.setValue(save);
    }



       public MutableLiveData<ArrayList<DatailInfo>> getDataSave() {
        return dataSave;
    }

}

