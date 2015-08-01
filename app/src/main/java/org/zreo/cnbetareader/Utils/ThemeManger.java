package org.zreo.cnbetareader.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.zreo.cnbetareader.R;


public class ThemeManger {


    public static void changeToTheme(Activity activity, Bundle saveData, int theme) {
        PrefKit.writeInt(activity, "theme", theme);
        activity.finish();
        Intent intent = new Intent(activity, activity.getClass());
        if (saveData != null) {
            intent.putExtras(saveData);
        }

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public static void changeToTheme(Activity activity, int theme) {
        changeToTheme(activity, null, theme);
    }

    public static int getCurrentTheme(Context context) {
        return PrefKit.getInt(context, "theme", 0);
    }
      public static void onActivityCreateSetTheme(Activity activity) {
        int[] theme = Theme[PrefKit.getInt(activity, "theme", 0)];
            activity.setTheme(theme[0]);
    }


  /*public static boolean isNightTheme(Context context) {
        return PrefKit.getInt(context, "theme", 0) == Theme.length - 1;
    }*/

    private static int[][] Theme = {{
            R.style.Theme_Basic,
            R.style.Theme_Basic_NoAnim
    }, {
            R.style.Theme_Basic_BROWN,
            R.style.Theme_Basic_BROWN_NoAnim
    }, {
            R.style.Theme_Basic_ORANGE,
            R.style.Theme_Basic_ORANGE_NoAnim
    }, {
            R.style.Theme_Basic_PURPLE,
            R.style.Theme_Basic_PURPLE_NoAnim
    }, {
            R.style.Theme_Basic_GREEN,
            R.style.Theme_Basic_GREEN_NoAnim
    }};
}