package com.project.communicationhub.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.communicationhub.fragments.ChatsFragment
import com.project.communicationhub.fragments.PeopleFragment

class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val numPages: Int = 2

    override fun getItemCount(): Int = numPages

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ChatsFragment()
        else -> PeopleFragment()
    }
}