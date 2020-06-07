package com.cobresun.factfulnewsandroid

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.cobresun.factfulnewsandroid.repositories.SharedPrefsUserDataRepository
import com.cobresun.factfulnewsandroid.settings.SettingsActivity
import com.cobresun.factfulnewsandroid.tabs.TabsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val categories = SharedPrefsUserDataRepository(
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        ).readUserCategories()
        viewPager.adapter = TabsPagerAdapter(this, categories)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = categories[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_setting) {
            // TODO: Get rid of SettingsActivity and just use a Fragment
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
