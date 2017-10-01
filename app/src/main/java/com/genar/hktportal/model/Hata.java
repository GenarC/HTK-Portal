package com.genar.hktportal.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hata implements Parcelable{

    @SerializedName("HataText")
    @Expose
    private String hataText;
    @SerializedName("ToplamHata")
    @Expose
    private String toplamHata;

    public Hata() {
    }

    public Hata(String hataText, String toplamHata) {
        this.hataText = hataText;
        this.toplamHata = toplamHata;
    }

    public String getHataText() {
        return hataText;
    }

    public void setHataText(String hataText) {
        this.hataText = hataText;
    }

    public String getToplamHata() {
        return toplamHata;
    }

    public void setToplamHata(String toplamHata) {
        this.toplamHata = toplamHata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hataText);
        dest.writeString(toplamHata);
    }

    private Hata(Parcel in){
        this.hataText = in.readString();
        this.toplamHata = in.readString();
    }
    public static final Parcelable.Creator<Hata> CREATOR = new Parcelable.Creator<Hata>() {

        @Override
        public Hata createFromParcel(Parcel source) {
            return new Hata(source);
        }

        @Override
        public Hata[] newArray(int size) {
            return new Hata[size];
        }
    };
}
