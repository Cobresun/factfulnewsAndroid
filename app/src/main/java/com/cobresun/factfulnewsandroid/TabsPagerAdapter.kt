package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabsPagerAdapter(fragmentManager: FragmentManager, private val numCategories: Int): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return TabFragment(position)
    }


    override fun getCount(): Int {
        return numCategories
    }
}