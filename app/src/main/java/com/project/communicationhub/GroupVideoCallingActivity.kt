package com.project.communicationhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}