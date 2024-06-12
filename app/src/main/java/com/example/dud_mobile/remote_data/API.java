package com.example.dud_mobile.remote_data;

import com.example.dud_mobile.models.*;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.*;

import java.util.List;

public interface API {

    @POST("register")
    Call<User> registrationNewUser(@Body User user);

    @POST("auth")
    Call<LoginResponse> checkLoginUser(@Body CurrentUser currentUser);

    @GET("lesson/getAllByLevel/{levelId}")
    Call<List<Lesson>> getLessonsByLevel(@Path("levelId") int levelId);

    @GET("lesson/get/{lessonId}/grammar")
    Call<GrammarLesson> getGrammarByLesson(@Path("lessonId") int lessonId);
}
