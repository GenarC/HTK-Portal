package com.genar.hktportal.api;

import com.genar.hktportal.response.MakineResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MakineService {

    @FormUrlEncoded
    @POST("cem/hktportal/db_functions.php")
    Call<MakineResponse> makineListesiGetir(@Field("a_operation") String operation);

}
