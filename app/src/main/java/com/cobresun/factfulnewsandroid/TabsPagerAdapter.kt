package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cobresun.factfulnewsandroid.models.Settings

class TabsPagerAdapter(
    fragmentManager: FragmentManager,
    private val tabTitles: Array<String>, private val numCategories: Int,
    private val settings: Settings
): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val tabsCache : Array<TabFragment?> = arrayOfNulls<TabFragment?>(numCategories)

    override fun getItem(position: Int): Fragment {
        if (tabsCache[position] == null) {
            tabsCache[position] = TabFragment(tabTitles[position], settings)
        }

        return tabsCache[position] ?: TabFragment(tabTitles[position], settings)
    }

    override fun getCount(): Int {
        return numCategories
    }
}