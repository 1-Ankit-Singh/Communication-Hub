package com.project.communicationhub

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.project.communicationhub.databinding.ActivityGroupVideoCallingBinding
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.MalformedURLException
import java.net.URL

class GroupVideoCallingActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var groupVideoCallingActivity: ActivityGroupVideoCallingBinding
    private lateinit var url: URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        groupVideoCallingActivity = ActivityGroupVideoCallingBinding.inflate(layoutInflater)
        setContentView(groupVideoCallingActivity.root)

        setSupportActionBar(groupVideoCallingActivity.toolbarGroupVideoCall)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
        }

        try {
            url = URL("https://meet.jit.si")
            val defaultOptions: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(url)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        groupVideoCallingActivity.startBtn.setOnClickListener {
            val options: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
                .setRoom(groupVideoCallingActivity.codeBox.text.toString())
                .setWelcomePageEnabled(false)
                .build()

            JitsiMeetActivity.launch(this, options)
        }

        groupVideoCallingActivity.joinBtn.setOnClickListener {
            val options: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
                .setRoom(groupVideoCallingActivity.codeBox.text.toString())
                .setWelcomePageEnabled(false)
                .build()

            JitsiMeetActivity.launch(this, options)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home  -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

}