package com.example.lazyload;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface SOService {

    @GET("api/query/datamodel?dm_name=test_ucdp_ged181&token=secret&limit=&offset=")
    Call<List<DatailInfo>> getAnswers(@Query("limit") Integer limit, @Query("offset") Integer offset);

}
