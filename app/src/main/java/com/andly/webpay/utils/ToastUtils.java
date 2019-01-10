package com.andly.webpay.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <pre>
 *     desc  : 吐司相关工具类
 * </pre>
 */
public class ToastUtils {

    private static int sGravity = Gravity.CENTER;
    private static int imgDrawable = 0;
    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore;
    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void showLongToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }


    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(Context context, CharSequence text, int duration) {
        if (isJumpWhenMore) cancelToast();
        if (context == null) {
            return;
        }
        if (sToast == null) {
            TextView tv = new TextView(context.getApplicationContext());
            tv.setPadding(30, 8, 30, 8);
            tv.setBackgroundDrawable(ScreenUtil.getBackDrawwable(context));
            tv.setTextSize(14.0F);
            tv.setTextColor(Color.parseColor("#3C9FF9"));
            tv.setText(text);
            sToast = new Toast(context.getApplicationContext());
            sToast.setDuration(duration);
            sToast.setView(tv);
        } else {
            TextView layout = (TextView) sToast.getView();
            layout.setText(text);
            sToast.setDuration(duration);
        }
        sToast.setGravity(sGravity, 0, 0);
        sToast.show();
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}