package com.ximendes.sumtwitter.ui.home

import androidx.lifecycle.MutableLiveData
import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.data.mapper.toTweetList
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import com.ximendes.sumtwitter.data.repository.user.UserRepository
import com.ximendes.sumtwitter.data.request.TweetsRequest
import com.ximendes.sumtwitter.util.constants.Constants
import com.ximendes.sumtwitter.util.livedata.SingleLiveEvent
import com.ximendes.sumtwitter.util.shared.BaseTimeLineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val repository: HomeRepository,
    private val userRepository: UserRepository
) : BaseTimeLineViewModel() {

    val tweets = MutableLiveData<List<Tweet>>()
    val error = SingleLiveEvent<Unit>()

    init {
        getUserTimeline()
    }

    fun getUserTimeline() {
        showProgressBar()
        val disposable = repository.getUserTimeline(buildTweetsRequest())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideProgressBar() }
            .subscribe({ tweetsResponseList ->
                fetchTweetsSuccess(tweetsResponseList.toTweetList())
            }, {
                showErrorState()
            })

        compositeDisposable.add(disposable)
    }

    private fun buildTweetsRequest(): TweetsRequest {
        return TweetsRequest(
            userRepository.getString(Constants.ACCESS_TOKEN_KEY),
            userRepository.getString(Constants.SECRET_KEY)
        )
    }

    private fun fetchTweetsSuccess(tweets: List<Tweet>) {
        this.tweets.postValue(tweets)
    }

    private fun showErrorState() = error.call()
}
