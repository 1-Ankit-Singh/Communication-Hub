package com.project.communicationhub.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.project.communicationhub.R
import com.project.communicationhub.databinding.ActivityNewsDetailsReadingBinding

const val NEWS_URL = "URL"

class NewsDetailsReadingActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var newsDetailsReadingActivity: ActivityNewsDetailsReadingBinding
    private lateinit var newsUrl: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsDetailsReadingActivity = ActivityNewsDetailsReadingBinding.inflate(layoutInflater)
        setContentView(newsDetailsReadingActivity.root)

        setSupportActionBar(newsDetailsReadingActivity.toolbarNewsDetailsReading)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        newsUrl = intent.getStringExtra(NEWS_URL).toString()

        val webSettings: WebSettings = newsDetailsReadingActivity.webView.settings
        webSettings.javaScriptEnabled = true
        newsDetailsReadingActivity.webView.loadUrl(newsUrl)
        newsDetailsReadingActivity.webView.webViewClient = WebViewClient()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (newsDetailsReadingActivity.webView.canGoBack()) {
            newsDetailsReadingActivity.webView.goBack()
        } else {
            finish()
            startActivity(Intent(this, NewsReadingActivity::class.java))
        }
    }

}