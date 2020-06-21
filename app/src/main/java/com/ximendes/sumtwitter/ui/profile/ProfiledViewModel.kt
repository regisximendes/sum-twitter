package com.ximendes.sumtwitter.ui.profile

import androidx.lifecycle.MutableLiveData
import com.ximendes.sumtwitter.data.mapper.toSimpleTweetList
import com.ximendes.sumtwitter.data.mapper.toTweet
import com.ximendes.sumtwitter.data.repository.home.TimeLineRepository
import com.ximendes.sumtwitter.data.repository.user.UserRepository
import com.ximendes.sumtwitter.data.request.TweetsRequest
import com.ximendes.sumtwitter.data.response.TweetResponse
import com.ximendes.sumtwitter.util.constants.Constants
import com.ximendes.sumtwitter.util.livedata.SingleLiveEvent
import com.ximendes.sumtwitter.util.shared.BaseTimeLineViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfiledViewModel(
    private val timeLineRepository: TimeLineRepository,
    private val userRepository: UserRepository
) : BaseTimeLineViewModel() {

    val tweets = MutableLiveData<List<String>>()
    val fullName = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val profileImageUrl = MutableLiveData<String>()

    val errorEvent = SingleLiveEvent<Unit>()

    fun getUserHome(userName: String) {
        val formattedUserName = formatUserNameToSearch(userName)
        showProgressBar()
        val disposable = timeLineRepository.getUserHome(formattedUserName, buildTweetsRequest())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideProgressBar() }
            .subscribe({ tweetsResponseList ->
                fetchTweetsSuccess(tweetsResponseList)
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

    private fun showErrorState() = errorEvent.call()
}
