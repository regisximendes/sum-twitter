package com.ximendes.sumtwitter.data.repository.home

import com.ximendes.sumtwitter.data.api.TimeLinesApi
import com.ximendes.sumtwitter.data.request.TweetsRequest
import com.ximendes.sumtwitter.data.response.TweetResponse
import io.reactivex.Single

class HomeRepositoryImpl(private val timeLinesApi: TimeLinesApi) : HomeRepository {

//    private val token = PreferencesHelper.getString(Constants.ACCESS_TOKEN_KEY)
//    private val secret = PreferencesHelper.getString(Constants.SECRET_KEY)
//    private val request = TweetsRequest(token.orEmpty(), secret.orEmpty())

    override fun getUserTimeline(request: TweetsRequest): Single<List<TweetResponse>> {
        return timeLinesApi.getUserTimeline(request)
    }

    override fun getUserHome(userName: String, request: TweetsRequest): Single<List<TweetResponse>> {
        return timeLinesApi.getUserHome(userName, request)
    }
}
