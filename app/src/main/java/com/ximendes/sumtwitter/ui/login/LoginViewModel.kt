package com.ximendes.sumtwitter.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.OAuthCredential
import com.ximendes.sumtwitter.data.enums.SessionState.LOGGED_IN
import com.ximendes.sumtwitter.data.enums.SessionState.LOGGED_OUT
import com.ximendes.sumtwitter.data.repository.login.LoginRepository
import com.ximendes.sumtwitter.data.repository.user.UserRepository
import com.ximendes.sumtwitter.util.constants.Constants
import com.ximendes.sumtwitter.util.livedata.SingleLiveEvent

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val loginSuccess = SingleLiveEvent<Unit>()
    val error = SingleLiveEvent<Unit>()
    val signInFlowEvent = SingleLiveEvent<Unit>()
    val isLoading = MutableLiveData<Boolean>().apply { value = false }

    init {
        checkLoggedUser()
    }

    private fun checkLoggedUser() {
        if (userRepository.hasUserLogged()) loginSuccess()
    }

    fun checkPendingResultTask() {
        showProgressBar()
        val sessionState = loginRepository.checkPendingResultTask().value ?: return

        when (sessionState) {
            LOGGED_OUT -> signInFlowEvent.call()
            LOGGED_IN -> loginSuccess.call()
        }
    }

    fun loginSuccess() {
        hideProgressBar()
        loginSuccess.call()
    }

    fun loginFail() {
        hideProgressBar()
        error.call()
    }

    fun onSaveCredentials(credential: OAuthCredential) {
        userRepository.saveString(Constants.ACCESS_TOKEN_KEY, credential.accessToken)
        userRepository.saveString(Constants.SECRET_KEY, credential.secret.orEmpty())
    }

    private fun showProgressBar() = isLoading.postValue(true)

    private fun hideProgressBar() = isLoading.postValue(false)
}
