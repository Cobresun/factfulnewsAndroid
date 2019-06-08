package com.cobresun.factfulnewsandroid.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.cobresun.factfulnewsandroid.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}