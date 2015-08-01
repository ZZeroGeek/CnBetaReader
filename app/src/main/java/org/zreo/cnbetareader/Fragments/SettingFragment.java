package org.zreo.cnbetareader.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
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

    private Preference preference;
    private boolean running = false;
    private Preference theme;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pre_setting);
        theme = findPreference("theme");
        theme.setSummary(getResources().getStringArray(R.array.theme_text)[ThemeManger.getCurrentTheme(getActivity())]);
        theme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new ChoiseThemeFragment().setCallBack(new ChoiseThemeFragment.callBack() {
                    @Override
                    public void onSelect(int which) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).changeTheme = true;
                        }
                        ImageLoader.getInstance().clearMemoryCache();
                        ThemeManger.changeToTheme(getActivity(), which);
                    }
                }).show(getActivity().getFragmentManager(),"theme");
                return false;
            }
        });
    }
      //  preference = findPreference(getString(R.string.clean_cache));
        /*preference.setSummary(getFileSize());
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                if (!running) {
                    running = true;
                    //   Toast.makeText(getActivity(),"正在清除缓存", Toast.LENGTH_SHORT).show();
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
    }*/
    }








