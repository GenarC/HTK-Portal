package com.genar.hktportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Makine {

    @SerializedName("no")
    @Expose
    private String no;
    @SerializedName("adi")
    @Expose
    private String adi;
    @SerializedName("ust")
    @Expose
    private String ust;
    @SerializedName("bolumrengi")
    @Expose
    private String bolumrengi;
    @SerializedName("hatagenelleme")
    @Expose
    private String hatagenelleme;
    @SerializedName("sapno")
    @Expose
    private String sapno;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getUst() {
        return ust;
    }

    public void setUst(String ust) {
        this.ust = ust;
    }

    public String getBolumrengi() {
        return bolumrengi;
    }

    public void setBolumrengi(String bolumrengi) {
        this.bolumrengi = bolumrengi;
    }

    public String getHatagenelleme() {
        return hatagenelleme;
    }

    public void setHatagenelleme(String hatagenelleme) {
        this.hatagenelleme = hatagenelleme;
    }

    public String getSapno() {
        return sapno;
    }

    public void setSapno(String sapno) {
        this.sapno = sapno;
    }

}
