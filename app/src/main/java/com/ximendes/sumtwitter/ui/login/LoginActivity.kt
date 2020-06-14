package com.ximendes.sumtwitter.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ximendes.sumtwitter.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
