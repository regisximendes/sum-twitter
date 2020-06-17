package com.ximendes.sumtwitter.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ximendes.sumtwitter.data.enums.SessionState.LOGGED_IN
import com.ximendes.sumtwitter.data.enums.SessionState.LOGGED_OUT
import com.ximendes.sumtwitter.data.repository.login.LoginRepository
import com.ximendes.sumtwitter.util.livedata.SingleLiveEvent

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    val loginSuccess = SingleLiveEvent<Unit>()
    val error = SingleLiveEvent<Unit>()
    val signInFlowEvent = SingleLiveEvent<Unit>()

    init {
        checkLoggedUser()
    }

    private fun checkLoggedUser() {
        val firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            loginSuccess()
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
        error.call()
    }

}