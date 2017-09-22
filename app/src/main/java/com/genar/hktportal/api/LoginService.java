package com.genar.hktportal.api;

import com.genar.hktportal.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("cem/hktportal/db_functions.php")
    Call<LoginResponse> login(@Field("a_operation") String operation, @Field("sicil") String sicilno, @Field("sifre") String sifre);
}
