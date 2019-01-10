package com.andly.webpay.utils;

import android.content.Context;

public class ResourcesUtils {
    private static int getResId(Context context, String type, String name) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }
    public static int getId(Context context, String name) {
        return getResId(context, "id", name);
    }

    public static int getStyle(Context context, String name) {
        return getResId(context, "style", name);
    }
}
