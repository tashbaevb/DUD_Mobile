package com.example.dud_mobile.remote_data;

import com.example.dud_mobile.models.CurrentUser;
import com.example.dud_mobile.models.LoginResponse;
import com.example.dud_mobile.models.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.*;

public interface API {

    @POST("register")
    Call<User> registrationNewUser(@Body User user);

    @POST("auth")
    Call<LoginResponse> checkLoginUser(@Body CurrentUser currentUser);
}
