package com.cobresun.factfulnewsandroid.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cobresun.factfulnewsandroid.ui.SettingsFragment
import android.content.Intent



class SettingsActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }

    override fun onBackPressed() {
        finish()
        val intent = Intent(this@SettingsActivity, MainActivity::class.java)
        startActivity(intent)
    }
}
