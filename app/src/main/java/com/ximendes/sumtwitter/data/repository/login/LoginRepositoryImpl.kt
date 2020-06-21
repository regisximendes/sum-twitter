package com.ximendes.sumtwitter.data.repository.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.ximendes.sumtwitter.data.enums.SessionState
import io.reactivex.Observable

class LoginRepositoryImpl : LoginRepository {


    override fun getBla(): Observable<AuthResult> {
        val firebaseAuth = FirebaseAuth.getInstance()
        val pendingResultTask: Task<AuthResult>? = firebaseAuth.pendingAuthResult

        return Observable.create { n ->
            pendingResultTask?.addOnSuccessListener(
                OnSuccessListener {
                    n.onNext(it)
                })
                ?.addOnFailureListener(
                    OnFailureListener {
                        n.onError(it)
                    })
        }
    }

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
