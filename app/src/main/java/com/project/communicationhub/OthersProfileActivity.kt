package com.project.communicationhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

const val ID = "ID"

class OthersProfileActivity : AppCompatActivity() {

    private lateinit var friendId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_profile)
        friendId = intent.getStringExtra(ID).toString()
        Toast.makeText(this, friendId, Toast.LENGTH_LONG).show()
    }
}