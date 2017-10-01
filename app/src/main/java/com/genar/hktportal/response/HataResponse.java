package com.genar.hktportal.response;

import com.genar.hktportal.model.Hata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HataResponse {

    @SerializedName("hatalarMost")
    @Expose
    private List<Hata> hatalarMost = null;
    @SerializedName("hatalarOperator")
    @Expose
    private List<Hata> hatalarOperator = null;
    @SerializedName("hatalarBolum")
    @Expose
    private List<Hata> hatalarBolum = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Hata> getHatalarMost() {
        return hatalarMost;
    }

    public void setHatalarMost(List<Hata> hatalarMost) {
        this.hatalarMost = hatalarMost;
    }

    public List<Hata> getHatalarOperator() {
        return hatalarOperator;
    }

    public void setHatalarOperator(List<Hata> hatalarOperator) {
        this.hatalarOperator = hatalarOperator;
    }

    public List<Hata> getHatalarBolum() {
        return hatalarBolum;
    }

    public void setHatalarBolum(List<Hata> hatalarBolum) {
        this.hatalarBolum = hatalarBolum;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
