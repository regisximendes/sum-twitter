package com.ximendes.sumtwitter.data.repository.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthResult
import com.ximendes.sumtwitter.data.enums.SessionState
import io.reactivex.Observable

interface LoginRepository {

    fun checkPendingResultTask(): LiveData<SessionState>

    fun getBla(): Observable<AuthResult>
}
