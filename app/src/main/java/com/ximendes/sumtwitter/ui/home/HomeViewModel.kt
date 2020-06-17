package com.ximendes.sumtwitter.ui.home

import androidx.lifecycle.MutableLiveData
import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.data.mapper.toTweetList
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import com.ximendes.sumtwitter.util.livedata.SingleLiveEvent
import com.ximendes.sumtwitter.util.shared.BaseTimeLineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: HomeRepository) : BaseTimeLineViewModel() {

    val tweets = MutableLiveData<List<Tweet>>()
    val error = SingleLiveEvent<Unit>()

    init {
        getUserTimeline()
    }

    private fun getUserTimeline() {
        showProgressBar()
        val disposable = repository.getUserTimeline()
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

    private fun fetchTweetsSuccess(tweets: List<Tweet>) {
        this.tweets.postValue(tweets)
    }

    private fun showErrorState() = error.call()
}
