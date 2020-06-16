package com.ximendes.sumtwitter.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import com.ximendes.sumtwitter.MainActivity
import com.ximendes.sumtwitter.R
import com.ximendes.sumtwitter.databinding.ActivityLoginBinding
import com.ximendes.sumtwitter.util.constants.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        viewListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signInFlowEvent.observe(this, Observer {
            twitterFirebaseSignIn()
        })

        viewModel.loginSuccess.observe(this, Observer {
            navigateToTimeline()
        })
    }

    private fun viewListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.checkPendingResultTask()
        }
    }

    private fun twitterFirebaseSignIn() {
        val provider = OAuthProvider.newBuilder(Constants.AUTH_PROVIDER)
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth
            .startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener {
                val a = it.credential as OAuthCredential
                val b = a.accessToken
                val c  = a.secret
                val d  = c
            }
            .addOnFailureListener {
                viewModel.loginFail()
            }
    }

    private fun navigateToTimeline() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
