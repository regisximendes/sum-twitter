package com.ximendes.sumtwitter.data.repository.home

import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single

interface HomeRepository {

    fun getUserTimeline(): Single<List<TweetResponse>>
}