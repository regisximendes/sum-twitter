package com.ximendes.sumtwitter.data.api

import com.ximendes.sumtwitter.data.request.TweetsRequest
import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface TimeLinesApi {

    @POST("/user/timeline")
    fun getUserTimeline(@Body request: TweetsRequest): Single<List<TweetResponse>>

    @POST("/user/home/{userName}")
    fun getUserHome(
        @Path("userName") userName: String,
        @Body request: TweetsRequest
    ): Single<List<TweetResponse>>
}
