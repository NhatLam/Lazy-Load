package com.example.lazyload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DatailInfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dyad_name")
    @Expose
    private String dyadName;
    @SerializedName("source_original")
    @Expose
    private String source;
    @SerializedName("country")
    @Expose
    private String country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDyadName() {
        return dyadName;
    }

    public void setDyadName(String dyadName) {
        this.dyadName = dyadName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}