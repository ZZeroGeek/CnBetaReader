package org.zreo.cnbetareader.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.format.Formatter;
import android.widget.Toast;

import org.zreo.cnbetareader.R;
import org.zreo.cnbetareader.Utils.FileKit;

import java.io.File;


public class SettingFragment extends PreferenceFragment {

    private Preference preference;
    private boolean running = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pre_setting);
        preference = findPreference(getString(R.string.clean_cache));
        preference.setSummary(getFileSize());
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                if (!running) {
                    running = true;
                    Toast.makeText(getActivity(),"正在清除缓存", Toast.LENGTH_SHORT).show();
                    new AsyncTask<Object, Object, Object>() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            FileKit.deleteDir(getActivity().getCacheDir());
                            try {
                                FileKit.deleteDir(getActivity().getExternalCacheDir());
                            } catch (Exception ignored) {
                            }
                            FileKit.deleteDir(new File(getActivity().getCacheDir().getAbsolutePath()));
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            Toast.makeText(getActivity(),"清除完成",Toast.LENGTH_SHORT).show();
                            preference.setSummary(getFileSize());
                            running = false;
                        }
                    }.execute();
                }
                return false;
            }
        });
    }
    private String getFileSize() {
        long size = 0;
        size += FileKit.getFolderSize(getActivity().getCacheDir());
        try {
            size += FileKit.getFolderSize(getActivity().getExternalCacheDir());
        } catch (Exception ignored) {
        }
        size += FileKit.getFolderSize(new File(getActivity().getCacheDir().getAbsolutePath() ));
        return Formatter.formatFileSize(getActivity(), size);
    }
    }




