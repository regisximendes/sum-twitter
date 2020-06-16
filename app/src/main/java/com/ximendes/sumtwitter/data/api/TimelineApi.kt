package com.ximendes.sumtwitter.data.api

import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single
import retrofit2.http.GET

interface TimelineApi {

    @GET("/user/timeline")
    fun getUserTimeline(): Single<List<TweetResponse>>
}