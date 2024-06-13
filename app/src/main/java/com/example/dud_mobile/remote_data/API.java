package com.example.dud_mobile.remote_data;

import com.example.dud_mobile.models.lessons.CheckAnswer;
import com.example.dud_mobile.models.lessons.GrammarLesson;
import com.example.dud_mobile.models.lessons.Lesson;
import com.example.dud_mobile.models.lessons.listening.ListeningLesson;
import com.example.dud_mobile.models.lessons.reading.ReadingLesson;
import com.example.dud_mobile.models.user.CurrentUser;
import com.example.dud_mobile.models.user.LoginResponse;
import com.example.dud_mobile.models.user.User;
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

    @GET("lesson/get/{lessonId}/reading")
    Call<ReadingLesson> getReadingByLesson(@Path("lessonId") int lessonId);

    @POST("reading/check/{readingId}")
    Call<Integer> checkReadingAnswers(@Path("readingId") int readingId, @Body List<CheckAnswer> answers);

    @GET("lesson/get/{lessonId}/listening")
    Call<ListeningLesson> getListeningByLesson(@Path("lessonId") int lessonId);

    @POST("listen/check/{listeningId}")
    Call<Integer> checkListeningAnswers(@Path("listeningId") int listeningId, @Body List<CheckAnswer> answers);
}
