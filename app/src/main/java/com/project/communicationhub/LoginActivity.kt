package com.project.communicationhub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.project.communicationhub.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var loginActivity: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginActivity.root)

        loginActivity.loginPassword.addTextChangedListener{
            loginActivity.login.isEnabled = !(loginActivity.loginEmail.text.isNullOrEmpty() || loginActivity.loginPassword.text.isNullOrEmpty())
        }

        loginActivity.loginEmail.addTextChangedListener {
            loginActivity.login.isEnabled = !(loginActivity.loginEmail.text.isNullOrEmpty() || loginActivity.loginPassword.text.isNullOrEmpty())
        }

        loginActivity.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            finish()
        }

        loginActivity.loginSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

    }

}