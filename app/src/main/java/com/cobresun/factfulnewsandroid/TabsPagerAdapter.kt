package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabsPagerAdapter(
    fragmentManager: FragmentManager,
    private val tabTitles: Array<String>,
    private val numCategories: Int,
    private val readTime: Int
): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val tabsCache : Array<TabFragment?> = arrayOfNulls<TabFragment?>(numCategories)

    override fun getItem(position: Int): Fragment {
        if (tabsCache[position] == null) {
            tabsCache[position] = TabFragment.newInstance(tabTitles[position], readTime)
        }

        return tabsCache[position] ?: TabFragment.newInstance(tabTitles[position], readTime)
    }

    override fun getCount(): Int {
        return numCategories
    }
}