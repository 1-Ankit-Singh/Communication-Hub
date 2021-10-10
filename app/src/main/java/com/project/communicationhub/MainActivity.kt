package com.project.communicationhub

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.project.communicationhub.adapters.ScreenSlidePagerAdapter
import com.project.communicationhub.books.BookReadingActivity
import com.project.communicationhub.databinding.ActivityMainBinding
import com.project.communicationhub.interaction.ProfileActivity
import com.project.communicationhub.news.NewsReadingActivity
import com.project.communicationhub.todolist.ToDoActivity

class MainActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var mainActivity: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)
        setSupportActionBar(mainActivity.toolbar)
        auth = FirebaseAuth.getInstance()
        mainActivity.viewPager.adapter = ScreenSlidePagerAdapter(this)
        TabLayoutMediator(mainActivity.tabs, mainActivity.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "CHATS"
                    }
                    1 -> {
                        tab.text = "PEOPLE"
                    }
                }
            }).attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.profile -> {
                profile()
                true
            }
            R.id.todo -> {
                todo()
                true
            }
            R.id.newsImage -> {
                newsReader()
                true
            }
            R.id.books -> {
                booksReader()
                true
            }
            R.id.searchGroupVideoCall -> {
                groupVideoCall()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun profile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    private fun todo() {
        startActivity(Intent(this, ToDoActivity::class.java))
    }

    private fun newsReader() {
        startActivity(Intent(this, NewsReadingActivity::class.java))
    }

    private fun booksReader() {
        startActivity(Intent(this, BookReadingActivity::class.java))
    }

    private fun groupVideoCall() {
        startActivity(Intent(this, GroupVideoCallingActivity::class.java))
    }

}