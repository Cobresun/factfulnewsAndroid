package com.cobresun.factfulnewsandroid.settings

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.repositories.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class SettingsFragment : PreferenceFragmentCompat() {

    @Inject lateinit var userPreferences : UserPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key.startsWith("category_")) {
            val newVal = (preference as CheckBoxPreference).isChecked
            userPreferences.writeUserCategories(preference.key, newVal)
        }
        return super.onPreferenceTreeClick(preference)
    }
}
