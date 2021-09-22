package com.project.communicationhub

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.project.communicationhub.adapters.ScreenSlidePagerAdapter
import com.project.communicationhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Initializing Variables
    private lateinit var mainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivity.root)
        setSupportActionBar(mainActivity.toolbar)
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

}