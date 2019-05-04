package com.cobresun.factfulnewsandroid.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.cobresun.factfulnewsandroid.CategoryUtils
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.TabsPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        // Create an instance of the tab layout from the view.
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        // Set the text for each tab.
        for(title in CategoryUtils.categories){
            tabLayout.addTab(tabLayout.newTab().setText(title.capitalize()))
        }
        // Set the tabs to fill the entire layout.
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        val viewPager = findViewById<ViewPager>(R.id.pager)

        viewPager.adapter = TabsPagerAdapter(supportFragmentManager, tabLayout.tabCount)

        viewPager.offscreenPageLimit = CategoryUtils.categories.size

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}
