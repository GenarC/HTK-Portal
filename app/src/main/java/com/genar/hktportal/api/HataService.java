package com.genar.hktportal.api;

import com.genar.hktportal.response.HataResponse;
import com.genar.hktportal.response.KnnResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cm_gn on 9/28/2017.
 */

public interface HataService {
    @FormUrlEncoded
    @POST("cem/hktportal/db_functions.php")
    Call<HataResponse> hataResponse(@Field("a_operation") String operation, @Field("topX") String topX);
}
