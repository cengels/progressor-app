package com.cengels.progressor.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.cengels.progressor.R
import com.cengels.progressor.activities.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val preferences: ViewGroup = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
        val toolbarLayout: View = inflater.inflate(R.layout.layout_appbar, container, false)
        val toolbar: Toolbar = toolbarLayout.findViewById(R.id.toolbar)

        preferences.addView(toolbarLayout, 0)

        (this.activity as MainActivity).setSupportActionBar(toolbar)
        this.setHasOptionsMenu(true)
        (this.activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Preferences"

        return preferences
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        this.setPreferencesFromResource(R.xml.preferences, rootKey)

        val feedbackLink = this.findPreference<Preference>("feedback")!!
        feedbackLink.setOnPreferenceClickListener { preference ->
            // TODO: Add feedback link
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_view_main_to_view_settings)!!.setVisible(false)
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
