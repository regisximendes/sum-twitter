package com.ximendes.sumtwitter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.data.mapper.toTweetList
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val tweets = MutableLiveData<List<Tweet>>()

    init {
        getUserTimeline()
    }

    private fun getUserTimeline() {
        val disposable = repository.getUserTimeline()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ tweetsResponseList ->
                fetchTweetsSuccess(tweetsResponseList.toTweetList())
            }, {
                handleError()
            })

        compositeDisposable.add(disposable)
    }

    private fun fetchTweetsSuccess(tweets: List<Tweet>) {
        this.tweets.postValue(tweets)
    }

    private fun handleError() {

    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
