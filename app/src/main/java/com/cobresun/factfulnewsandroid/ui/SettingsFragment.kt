package com.cobresun.factfulnewsandroid.ui

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.repositories.impl.SharedPrefsUserDataRepository

open class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val readTimePreference: SeekBarPreference? = findPreference("read_time")
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if(preference.key.startsWith("category_")){
            val thisContext = context
            if (thisContext != null) {
                val prefs = SharedPrefsUserDataRepository(thisContext)
                val newVal = (preference as CheckBoxPreference).isChecked
                prefs.writeUserCategories(preference.key, newVal)
            }
        }
        return super.onPreferenceTreeClick(preference)
    }
}