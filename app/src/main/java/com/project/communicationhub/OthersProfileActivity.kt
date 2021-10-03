package com.project.communicationhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

const val NAME = "userName"
const val IMAGE = "thumbImage"

class OthersProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_profile)
    }
}