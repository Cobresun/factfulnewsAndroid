package com.cobresun.factfulnewsandroid.ui

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.cobresun.factfulnewsandroid.R
import com.cobresun.factfulnewsandroid.repositories.impl.SharedPrefsUserDataRepository
import com.cobresun.factfulnewsandroid.ui.activities.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {
    var prefs : SharedPrefsUserDataRepository? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        if(prefs == null) {
            var thisContext = context
            if (thisContext != null)
                prefs = SharedPrefsUserDataRepository(thisContext)
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if(preference.key.startsWith("category_")){
            var newVal = (preference as CheckBoxPreference).isChecked
            prefs?.writeUserCategories(preference.key, newVal)

            // TODO refresh main activity without needing to restart app
        }
        return super.onPreferenceTreeClick(preference)
    }
}