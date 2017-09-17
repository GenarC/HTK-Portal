package com.genar.hktportal.api;

import com.genar.hktportal.response.KnnResponse;
import com.genar.hktportal.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KnnApi {
    @FormUrlEncoded
    @POST("cem/hktportal/db_functions.php")
    Call<KnnResponse> knnResponse(@Field("a_operation") String operation, @Field("sicil") String sicil, @Field("tarih") String tarih, @Field("yer") String yer, @Field("makno") String makno, @Field("gorev") String gorev, @Field("aciklama") String aciklama);
}
