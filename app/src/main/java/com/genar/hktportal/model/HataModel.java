package com.genar.hktportal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by cm_gn on 9/27/2017.
 */

public class HataModel implements Parcelable{

    private String hataText;
    private int adet;

    public HataModel() {
    }

    public HataModel(String hataText, int adet) {
        this.hataText = hataText;
        this.adet = adet;
    }

    public String getHataText() {
        return hataText;
    }

    public void setHataText(String hataText) {
        this.hataText = hataText;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hataText);
        dest.writeInt(adet);
    }

    private HataModel(Parcel in){
        this.hataText = in.readString();
        this.adet = in.readInt();
    }
    public static final Parcelable.Creator<HataModel> CREATOR = new Parcelable.Creator<HataModel>() {

        @Override
        public HataModel createFromParcel(Parcel source) {
            return new HataModel(source);
        }

        @Override
        public HataModel[] newArray(int size) {
            return new HataModel[size];
        }
    };
}
