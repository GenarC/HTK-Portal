package com.genar.hktportal.model;

public class Kkn {

    private String no;
    private String sicil;
    private String tarih;
    private String yer;
    private String makno;
    private String gorev;
    private String aciklama;

    public Kkn() {
    }

    public Kkn(String no, String sicil, String tarih, String yer, String makno, String gorev, String aciklama) {
        this.no = no;
        this.sicil = sicil;
        this.tarih = tarih;
        this.yer = yer;
        this.makno = makno;
        this.gorev = gorev;
        this.aciklama = aciklama;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSicil() {
        return sicil;
    }

    public void setSicil(String sicil) {
        this.sicil = sicil;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getYer() {
        return yer;
    }

    public void setYer(String yer) {
        this.yer = yer;
    }

    public String getMakno() {
        return makno;
    }

    public void setMakno(String makno) {
        this.makno = makno;
    }

    public String getGorev() {
        return gorev;
    }

    public void setGorev(String gorev) {
        this.gorev = gorev;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
