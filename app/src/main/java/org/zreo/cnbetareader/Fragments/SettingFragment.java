package org.zreo.cnbetareader.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.widget.Toast;
import android.text.format.Formatter;


import com.nostra13.universalimageloader.core.ImageLoader;
import org.zreo.cnbetareader.Activitys.MainActivity;
import org.zreo.cnbetareader.R;
import org.zreo.cnbetareader.Utils.FileKit;
import org.zreo.cnbetareader.Utils.ThemeManger;

import java.io.File;


public class SettingFragment extends PreferenceFragment {

    private boolean running = false;
    private int themeid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pre_setting);
        Preference theme = findPreference("theme");
        theme.setOnPreferenceClickListener(onPreferenceClickListener);
    }

    Preference.OnPreferenceClickListener onPreferenceClickListener = new Preference.OnPreferenceClickListener() {
        public boolean onPreferenceClick(Preference preference) {
            if (preference.getKey().equals("theme")) {
                new AlertDialog.Builder(getActivity()).setTitle(R.string.theme)
                        .setSingleChoiceItems(R.array.theme_text, ThemeManger.getCurrentTheme(getActivity()), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                themeid = which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (getActivity() instanceof MainActivity) {
                                    ((MainActivity) getActivity()).changeTheme = true;
                                }
                                ThemeManger.changeToTheme(getActivity(), themeid);
                            }
                        }).create().show();

            }
            return false;
        }

    };
}







