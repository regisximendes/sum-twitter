package com.ximendes.sumtwitter.data.api

import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TimeLinesApi {

    @GET("/user/timeline")
    fun getUserTimeline(): Single<List<TweetResponse>>

    @GET("/user/home/{userName}")
    fun getUserHome(@Path("userName") userName: String): Single<List<TweetResponse>>
}