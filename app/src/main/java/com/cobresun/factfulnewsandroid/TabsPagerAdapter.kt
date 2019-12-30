package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val categories: Array<String>,
    private val readTime: Int
): FragmentStateAdapter(fragmentActivity) {
    private val tabsCache : Array<TabFragment?> = arrayOfNulls<TabFragment?>(categories.size)

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        if (tabsCache[position] == null) {
            tabsCache[position] = TabFragment.newInstance(categories[position], readTime)
        }

        return tabsCache[position] ?: TabFragment.newInstance(categories[position], readTime)
    }
}