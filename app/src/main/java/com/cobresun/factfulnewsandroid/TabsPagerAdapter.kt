package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cobresun.factfulnewsandroid.models.Settings

class TabsPagerAdapter(fragmentManager: FragmentManager, tabTitles: Array<String>, private val numCategories: Int, settings: Settings): FragmentStatePagerAdapter(fragmentManager) {
    val tabsCache : Array<TabFragment?> = arrayOfNulls<TabFragment?>(numCategories)
    val tabTitles = tabTitles
    val settings = settings

    override fun getItem(position: Int): Fragment {
        if(tabsCache[position] == null){
            tabsCache[position] = TabFragment(tabTitles[position], settings)
        }

        return tabsCache[position] ?: TabFragment(tabTitles[position], settings)
    }


    override fun getCount(): Int {
        return numCategories
    }
}