package com.ximendes.sumtwitter.data.repository.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.ximendes.sumtwitter.data.enums.SessionState


class LoginRepositoryImpl : LoginRepository {

    override fun checkPendingResultTask(): LiveData<SessionState> {
        val firebaseAuth = FirebaseAuth.getInstance()
        val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult
        val data = MutableLiveData<SessionState>()

        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener(
                    OnSuccessListener {
                        data.value = SessionState.LOGGED_IN
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        data.value = SessionState.LOGGED_OUT
                    })
        } else {
            data.value = SessionState.LOGGED_OUT
        }
        return data
    }
}