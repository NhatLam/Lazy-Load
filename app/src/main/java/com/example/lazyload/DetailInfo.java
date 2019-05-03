package com.example.lazyload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DetailInfo implements Serializable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("dyad_name")
    @Expose
    public String dyadName;
    @SerializedName("source_original")
    @Expose
    public String source;
    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;

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

    public DetailInfo(String id, String dyadName, String source, String country, String latitude, String longitude) {
        this.id = id;
        this.dyadName = dyadName;
        this.source = source;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}