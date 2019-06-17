package com.cobresun.factfulnewsandroid.ui

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.repositories.impl.SharedPrefsUserDataRepository
import com.cobresun.factfulnewsandroid.ui.activities.MainActivity

open class SettingsFragment() : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if(preference.key.startsWith("category_")){
            var thisContext = context
            if (thisContext != null) {
                var prefs = SharedPrefsUserDataRepository(thisContext)
                var newVal = (preference as CheckBoxPreference).isChecked
                prefs?.writeUserCategories(preference.key, newVal)
            }
        }
        return super.onPreferenceTreeClick(preference)
    }
}