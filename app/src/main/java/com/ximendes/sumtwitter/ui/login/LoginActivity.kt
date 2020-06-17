package com.ximendes.sumtwitter.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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

    private fun observeViewModel() = with(viewModel) {
        signInFlowEvent.observe(this@LoginActivity, Observer {
            twitterFirebaseSignIn()
        })

        loginSuccess.observe(this@LoginActivity, Observer {
            navigateToTimeline()
        })

        error.observe(this@LoginActivity, Observer {
            showErrorDialog()
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
                viewModel.loginSuccess()
                val a = it.credential as OAuthCredential
                val b = a.accessToken
                val c = a.secret
                val d = c
            }
            .addOnFailureListener {
                viewModel.loginFail()
            }
    }

    private fun navigateToTimeline() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error_title))
            .setMessage(getString(R.string.error_message))
            .setPositiveButton(android.R.string.yes, null)
            .show()
    }
}
