package com.andly.webpay.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by andly on 2017/9/26.
 */

public class ScreenUtil {
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public static Drawable getBackDrawwable(Context context) {
        int var1 = getDm(context, 0.7F);
        int var2 = getDm(context, 6.0F);
        int var3 = Color.parseColor("#D4D4D4");
        int var4 = Color.parseColor("#FFFFFF");
        GradientDrawable var5;
        (var5 = new GradientDrawable()).setColor(var4);
        var5.setCornerRadius((float) var2);
        var5.setStroke(var1, var3);
        return var5;
    }

    public static int getDm(Context context, float var1) {
        if (var1 <= 0.0F) {
            return (int) var1;
        } else {
            float var2 = context.getResources().getDisplayMetrics().density;
            return (int) (var1 * var2 + 0.5F);
        }
    }



}
