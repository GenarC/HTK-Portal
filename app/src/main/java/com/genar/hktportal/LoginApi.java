package com.genar.hktportal;

import com.genar.hktportal.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("cem/hktportal/db_functions.php")
    Call<LoginResponse> loginResponse(@Field("a_operation") String operation, @Field("sicil") String sicilno, @Field("sifre") String sifre);
}
