package com.cobresun.factfulnewsandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val categories: Array<String>
): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment = TabFragment.newInstance(categories[position])
}