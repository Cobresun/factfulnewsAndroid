package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabsPagerAdapter(fragmentManager: FragmentManager, tabTitles: Array<String>, private val numCategories: Int): FragmentStatePagerAdapter(fragmentManager) {
    val tabsCache : Array<TabFragment?> = arrayOfNulls<TabFragment?>(numCategories)
    val tabTitles = tabTitles

    override fun getItem(position: Int): Fragment {
        if(tabsCache[position] == null){
            tabsCache[position] = TabFragment(tabTitles[position])
        }

        return tabsCache[position] ?: TabFragment(tabTitles[position])
    }


    override fun getCount(): Int {
        return numCategories
    }
}