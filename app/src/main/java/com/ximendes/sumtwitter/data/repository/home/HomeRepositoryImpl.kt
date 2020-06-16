package com.ximendes.sumtwitter.data.repository.home

import com.ximendes.sumtwitter.data.api.HomeApi
import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single

class HomeRepositoryImpl(private val homeApi: HomeApi): HomeRepository {

    override fun getUserTimeline(): Single<List<TweetResponse>> {
        return homeApi.getUserTimeline()
    }
}