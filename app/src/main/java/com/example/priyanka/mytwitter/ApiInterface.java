package com.example.priyanka.mytwitter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by PRIYANKA on 21-07-2017.
 */

public interface ApiInterface {
    @GET("search/tweets.json")
    Call<Trends> GetTrends(@Header("Authorization")String header , @Query("q") int q);
//    @GET("statuses/user_timeline.json")
//    Call<ArrayList<String>> getUserTimeline(@Header("Authorization") String header);
}
