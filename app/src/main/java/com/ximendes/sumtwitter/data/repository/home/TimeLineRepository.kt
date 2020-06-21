package com.ximendes.sumtwitter.data.repository.home

import com.ximendes.sumtwitter.data.request.TweetsRequest
import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single

interface TimeLineRepository {

    fun getUserTimeline(request: TweetsRequest): Single<List<TweetResponse>>
    fun getUserHome(userName: String, request: TweetsRequest): Single<List<TweetResponse>>
}
