package com.ximendes.sumtwitter.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.ximendes.sumtwitter.data.mapper.toSimpleTweetList
import com.ximendes.sumtwitter.data.mapper.toTweet
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import com.ximendes.sumtwitter.data.response.TweetResponse
import com.ximendes.sumtwitter.util.shared.BaseTimeLineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DashboardViewModel(private val repository: HomeRepository) : BaseTimeLineViewModel() {

    val tweets = MutableLiveData<List<String>>()
    val fullName = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val profileImageUrl =  MutableLiveData<String>()

    fun getUserHome(userName: String) {
        val formattedUserName = formatUserNameToSearch(userName)

        val disposable = repository.getUserHome(formattedUserName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ tweetsResponseList ->
                fetchTweetsSuccess(tweetsResponseList)
            }, {
                handleError()
            })

        compositeDisposable.add(disposable)
    }

    private fun formatUserNameToSearch(userName: String): String {
        return userName.replace("@", "")
    }

    private fun fetchTweetsSuccess(tweets: List<TweetResponse>) {
        this.tweets.postValue(tweets.toSimpleTweetList())
        setUserInfo(tweets)
    }

    private fun setUserInfo(tweets: List<TweetResponse>) {
        val user = tweets.firstOrNull()?.toTweet()
        fullName.postValue(user?.fullName)
        userName.postValue(user?.userName)
        description.postValue(user?.description)
        profileImageUrl.postValue(user?.profileImageUrl)
    }

    private fun handleError() {

    }
}
