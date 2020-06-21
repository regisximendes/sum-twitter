package com.ximendes.sumtwitter.data.repository.home

import com.ximendes.sumtwitter.data.api.TimeLinesApi
import com.ximendes.sumtwitter.data.request.TweetsRequest
import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single

class HomeRepositoryImpl(private val timeLinesApi: TimeLinesApi) : HomeRepository {

    override fun getUserTimeline(request: TweetsRequest): Single<List<TweetResponse>> {
        return timeLinesApi.getUserTimeline(request)
    }

    override fun getUserHome(
        userName: String,
        request: TweetsRequest
    ): Single<List<TweetResponse>> {
        return timeLinesApi.getUserHome(userName, request)
    }
}
