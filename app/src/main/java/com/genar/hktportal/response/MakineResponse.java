package com.genar.hktportal.response;

import java.util.List;

import com.genar.hktportal.model.Makine;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakineResponse {

    @SerializedName("makineler")
    @Expose
    private List<Makine> makineler = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Makine> getMakineler() {
        return makineler;
    }

    public void setMakineler(List<Makine> makineler) {
        this.makineler = makineler;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
