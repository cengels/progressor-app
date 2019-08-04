package com.cengels.progressor.fragments

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.cengels.progressor.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        this.setPreferencesFromResource(R.xml.preferences, rootKey)

        val feedbackLink = this.findPreference<Preference>("feedback")!!
        feedbackLink.setOnPreferenceClickListener { preference ->
            // TODO: Add feedback link
            false
        }
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
