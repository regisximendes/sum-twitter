package com.ximendes.sumtwitter.data.repository.home

import com.ximendes.sumtwitter.data.api.TimeLinesApi
import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single

class HomeRepositoryImpl(private val timeLinesApi: TimeLinesApi): HomeRepository {

    override fun getUserTimeline(): Single<List<TweetResponse>> {
        return timeLinesApi.getUserTimeline()
    }

    override fun getUserHome(userName: String): Single<List<TweetResponse>> {
        return timeLinesApi.getUserHome(userName)
    }
}