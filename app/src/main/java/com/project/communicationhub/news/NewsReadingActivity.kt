package com.project.communicationhub.news

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.project.communicationhub.MainActivity
import com.project.communicationhub.R
import com.project.communicationhub.databinding.ActivityNewsReadingBinding

class NewsReadingActivity : AppCompatActivity() {

    // Initialising Variables
    private lateinit var newsReadingActivity: ActivityNewsReadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsReadingActivity = ActivityNewsReadingBinding.inflate(layoutInflater)
        setContentView(newsReadingActivity.root)

        setSupportActionBar(newsReadingActivity.toolbarNews)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
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
        startActivity(
            Intent(this, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

}