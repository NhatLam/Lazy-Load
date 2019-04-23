package com.example.lazyload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DatailInfo implements Serializable {

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

    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

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

    public DatailInfo(String id, String dyadName, String source, String country, String latitude, String longitude) {
        this.id = id;
        this.dyadName = dyadName;
        this.source = source;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}