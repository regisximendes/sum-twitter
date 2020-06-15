package com.ximendes.sumtwitter.data.repository.login

import androidx.lifecycle.LiveData
import com.ximendes.sumtwitter.data.enums.SessionState

interface LoginRepository {

    fun checkPendingResultTask(): LiveData<SessionState>
}