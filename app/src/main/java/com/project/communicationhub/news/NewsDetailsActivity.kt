package com.project.communicationhub.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.communicationhub.databinding.ActivityNewsDetailsBinding

const val TITLE = "title"
const val CONTENT = "content"
const val DESCRIPTION = "description"
const val IMAGE = "image"
const val URL = "url"

class NewsDetailsActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var newsDetailsActivity: ActivityNewsDetailsBinding
    private lateinit var title: String
    private lateinit var content: String
    private lateinit var description: String
    private lateinit var image: String
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsDetailsActivity = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(newsDetailsActivity.root)

        title = intent.getStringExtra(TITLE).toString()
        content = intent.getStringExtra(CONTENT).toString()
        description = intent.getStringExtra(DESCRIPTION).toString()
        image = intent.getStringExtra(IMAGE).toString()
        url = intent.getStringExtra(URL).toString()

    }
}