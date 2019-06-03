package com.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by admin on 2019-02-04.
 */

public class UtilTools {

    //设置字体
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}