package com.cobresun.factfulnewsandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.cobresun.factfulnewsandroid.repositories.impl.SharedPrefsUserDataRepository
import com.cobresun.factfulnewsandroid.settings.SettingsActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupTabLayout()
    }

    private fun setupTabLayout() {
        val categories = SharedPrefsUserDataRepository(applicationContext).readUserCategories()
        categories.forEach { tabLayout.addTab(tabLayout.newTab().setText(it.capitalize())) }

        val readTime = SharedPrefsUserDataRepository(applicationContext).readUserReadTime()

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        viewPager.adapter = TabsPagerAdapter(supportFragmentManager, categories, tabLayout.tabCount, readTime)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_setting) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
