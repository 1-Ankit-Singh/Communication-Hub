package com.project.communicationhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.project.communicationhub.auth.LoginActivity
import com.project.communicationhub.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var splashScreenActivity: ActivitySplashScreenBinding
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenActivity = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenActivity.root)

        Thread {
            Thread.sleep(1000)
            if (auth.currentUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }.start()

    }
}