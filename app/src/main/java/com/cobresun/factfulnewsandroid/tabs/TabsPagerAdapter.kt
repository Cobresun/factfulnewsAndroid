package com.cobresun.factfulnewsandroid.tabs

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val categories: List<Category>
): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): TabFragment {
        val tabFragment = TabFragment()
        tabFragment.arguments = bundleOf("category" to categories[position].value)
        return tabFragment
    }
}
