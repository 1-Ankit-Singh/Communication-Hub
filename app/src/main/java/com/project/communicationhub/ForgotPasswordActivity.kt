package com.project.communicationhub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.project.communicationhub.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    // Initializing Variables
    lateinit var forgotPasswordActivity: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotPasswordActivity = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(forgotPasswordActivity.root)

        forgotPasswordActivity.fgpEmail.addTextChangedListener {
            forgotPasswordActivity.send.isEnabled = !it.isNullOrEmpty()
        }

        forgotPasswordActivity.backImageButton2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

}