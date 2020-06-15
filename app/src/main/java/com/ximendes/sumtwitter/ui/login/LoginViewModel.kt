package com.ximendes.sumtwitter.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ximendes.sumtwitter.R
import com.ximendes.sumtwitter.data.enums.SessionState.LOGGED_IN
import com.ximendes.sumtwitter.data.enums.SessionState.LOGGED_OUT
import com.ximendes.sumtwitter.data.repository.login.LoginRepository
import com.ximendes.sumtwitter.util.livedata.SingleLiveEvent
import com.ximendes.sumtwitter.util.resourceprovider.ResourceProvider

class LoginViewModel(
    private val resourceProvider: ResourceProvider,
    private val repository: LoginRepository
) : ViewModel() {

    val loginSuccess = SingleLiveEvent<Unit>()
    val errorMessage = SingleLiveEvent<String>()
    val signInFlowEvent = SingleLiveEvent<Unit>()

    init {
        checkLoggedUser()
    }

    private fun checkLoggedUser() {
        val firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            loginSuccess.call()
        }
    }

    fun checkPendingResultTask() {
        val sessionState = repository.checkPendingResultTask().value ?: return

        when (sessionState) {
            LOGGED_OUT -> signInFlowEvent.call()
            LOGGED_IN -> loginSuccess.call()
        }
    }

    fun loginSuccess() {
        loginSuccess.call()
    }

    fun loginFail() {
        errorMessage.postValue(resourceProvider.getString(R.string.login_fail_message))
    }
}