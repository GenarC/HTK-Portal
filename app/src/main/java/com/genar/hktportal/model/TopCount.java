package com.genar.hktportal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopCount {

    @SerializedName("topCount")
    @Expose
    private String topCount;

    public String getTopCount() {
        return topCount;
    }

    public void setTopCount(String topCount) {
        this.topCount = topCount;
    }

}