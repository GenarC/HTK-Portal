package com.genar.hktportal.api;

import com.genar.hktportal.model.TopCount;
import com.genar.hktportal.response.HataResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TopCountService {
    @FormUrlEncoded
    @POST("cem/hktportal/db_functions.php")
    Call<TopCount> getTopCount (@Field("a_operation") String operation);
}
