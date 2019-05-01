package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabsPagerAdapter(fragmentManager: FragmentManager, numCategories: Int):
    FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        return TabFragment(position)
    }


    override fun getCount(): Int {
        return 8                    //TODO replace with variable taking number of pages
    }
}