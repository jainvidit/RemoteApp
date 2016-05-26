package com.haloappstudio.morld;

import com.haloappstudio.morld.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by suheb on 26/5/16.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);
    @GET("device/{device_id}/get_status")
    Call<Integer> get_status(@Path("device_id") Integer device_id);

}
