package com.example.factfulnewsandroid

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.runner.RunWith

import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.cobresun.factfulnewsandroid.R
import androidx.test.espresso.contrib.RecyclerViewActions
import com.cobresun.factfulnewsandroid.ui.activities.MainActivity
import org.junit.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class BasicTests {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Test
    fun openAppClickFifthArticle() {
        Thread.sleep(1000)  // TODO: Stop sleeping while we make network request
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(5, click()))
    }
}