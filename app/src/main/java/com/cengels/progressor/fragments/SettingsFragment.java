package com.cengels.progressor.fragments;

import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.cengels.progressor.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        final Preference feedbackLink = findPreference("feedback");
        assert feedbackLink != null;
        feedbackLink.setOnPreferenceClickListener(preference -> {
            // TODO: Add feedback link
            return false;
        });
    }
}
