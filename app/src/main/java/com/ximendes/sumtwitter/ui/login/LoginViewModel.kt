package com.ximendes.sumtwitter.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.OAuthCredential
import com.ximendes.sumtwitter.data.repository.user.UserRepository
import com.ximendes.sumtwitter.util.constants.Constants
import com.ximendes.sumtwitter.util.livedata.SingleLiveEvent

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val timeLineNavigationEvent = SingleLiveEvent<Unit>()
    val errorEvent = SingleLiveEvent<Unit>()
    val signInFlowEvent = SingleLiveEvent<Unit>()
    val isLoading = MutableLiveData<Boolean>().apply { value = false }

    init {
        checkLoggedUser()
    }

    fun startLoginFlow() {
        showProgressBar()
        signInFlowEvent.call()
    }

    fun loginSuccess() {
        hideProgressBar()
        timeLineNavigationEvent.call()
    }

    fun loginFail() {
        hideProgressBar()
        errorEvent.call()
    }

    fun onSaveCredentials(credential: OAuthCredential) {
        userRepository.saveString(Constants.ACCESS_TOKEN_KEY, credential.accessToken)
        userRepository.saveString(Constants.SECRET_KEY, credential.secret.orEmpty())
    }

    private fun checkLoggedUser() {
        if (userRepository.hasUserLogged()) {
            loginSuccess()
        }
    }

    private fun showProgressBar() = isLoading.postValue(true)

    private fun hideProgressBar() = isLoading.postValue(false)
}
