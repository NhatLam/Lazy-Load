package com.example.lazyload;

public class ApiUtils {
    public static final String BASE_URL = "https://rtlab02.rtworkspace.com/";


    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
