package com.NaviDemo;

/**
 * Created by admin on 2019-02-02.
 */


import android.app.Activity;
import android.content.Intent;

public class NormalUtils {

    public static void gotoSettings(Activity activity) {
        Intent it = new Intent(activity, DemoNaviSettingActivity.class);
        activity.startActivity(it);
    }

    public static String getTTSAppID() {
        return "15515837";
    }
}
