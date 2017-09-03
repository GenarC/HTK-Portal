package com.genar.hktportal.model;

import java.io.Serializable;

/**
 * Created by cm_gn on 31.08.2017.
 */

public class Person implements Serializable {

    private String no;
    private String adi;
    private String soyadi;
    private String sifre;
    private String eposta;

    public Person() {
    }

    public Person(String no, String adi, String soyadi, String sifre, String eposta) {
        this.no = no;
        this.adi = adi;
        this.soyadi = soyadi;
        this.sifre = sifre;
        this.eposta = eposta;
    }

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

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }
}
