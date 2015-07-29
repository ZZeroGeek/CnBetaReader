package org.zreo.cnbetareader.Fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import org.zreo.cnbetareader.R;


public class SettingFragment extends PreferenceFragment {

   private Preference preference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pre_setting);


    }
}

