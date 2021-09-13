package com.project.communicationhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.communicationhub.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var splashScreenActivity: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenActivity = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenActivity.root)

        Thread {
            Thread.sleep(1000)
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }.start()

    }
}