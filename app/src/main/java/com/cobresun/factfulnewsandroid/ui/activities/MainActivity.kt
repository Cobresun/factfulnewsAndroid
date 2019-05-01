package com.cobresun.factfulnewsandroid.ui.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cobresun.factfulnewsandroid.CategoryUtils
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.backend.api.ApiService
import com.cobresun.factfulnewsandroid.backend.api.FetchResponse
import com.cobresun.factfulnewsandroid.backend.models.Article
import com.cobresun.factfulnewsandroid.TabsPagerAdapter
import com.cobresun.factfulnewsandroid.ui.adapters.ArticlesAdapter
import com.google.android.material.tabs.TabLayout
//import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        // Create an instance of the tab layout from the view.
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        // Set the text for each tab.
        for(title in CategoryUtils.categoryFromTabIndex){
            tabLayout.addTab(tabLayout.newTab().setText(title.capitalize()))
        }
        // Set the tabs to fill the entire layout.
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        val viewPager = findViewById<ViewPager>(R.id.pager)

        var adapter: TabsPagerAdapter
        adapter = TabsPagerAdapter(supportFragmentManager, tabLayout.tabCount)

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

//    private fun showArticles(articles: List<Article>) {
//        val itemOnClick: (View, Int, Int) -> Unit = { _, position, _ ->
//            // The URL belonging to the selected article.
//            val articleUrl = articles[position].url
//            // Initialize the Custom Tabs Intent Builder.
//            val ctBuilder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
//            // Show the website's title in the Custom Tab.
//            ctBuilder.setShowTitle(true)
//            // Set the toolbar's color to be the app's primary color.
//            ctBuilder.setToolbarColor(ResourcesCompat.getColor(resources,
//                R.color.colorPrimary, null))
//            // Build the Custom Tabs intent.
//            val ctIntent: CustomTabsIntent = ctBuilder.build()
//            // Parse the article URL as a Uri, and then launch the
//            // intent.
//            ctIntent.launchUrl(this@MainActivity, Uri.parse(articleUrl))
//        }
//
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter =
//                ArticlesAdapter(this@MainActivity, articles, itemOnClick)
//        }
//    }
}
