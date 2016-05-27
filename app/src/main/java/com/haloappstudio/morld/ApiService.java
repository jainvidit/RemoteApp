package com.haloappstudio.morld;


import com.haloappstudio.morld.models.Device;
import com.haloappstudio.morld.models.Location;
import com.haloappstudio.morld.models.LoginResponse;

import java.util.List;

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
    @GET("user/{user_id}/locations")
    Call<List<Location>> locations(@Path("user_id") Integer user_id);
    @GET("location/{location_id}/devices")
    Call<List<Device>> devices(@Path("location_id") Integer location_id);
    @GET("device/{device_id}/set_status/{status}")
    Call<String> set_status(@Path("device_id") Integer device_id, @Path("status") Integer status);

}
