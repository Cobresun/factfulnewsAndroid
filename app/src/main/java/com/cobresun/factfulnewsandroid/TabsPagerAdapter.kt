package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabsPagerAdapter(fragmentManager: FragmentManager, numCategories: Int):
    FragmentStatePagerAdapter(fragmentManager) {
    var numCategories = numCategories

    val tabsCache : Array<TabFragment?> = arrayOfNulls<TabFragment?>(numCategories)

    override fun getItem(position: Int): Fragment {
        if(tabsCache[position] == null){
            tabsCache[position] = TabFragment(position)
        }

        return tabsCache[position] ?: TabFragment(position)
    }


    override fun getCount(): Int {
        return numCategories
    }
}