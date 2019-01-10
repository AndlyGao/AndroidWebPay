package com.andly.webpay.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.andly.webpay.utils.ResourcesUtils;


/**
 * Created by andly on 2017/9/26.
 */

public class BaseDialog extends Dialog {
    public Activity mContext;

    public BaseDialog(Context context) {
        super(context, ResourcesUtils.getStyle(context, "pay_dialog_style"));
        requestWindowFeature(1);
        setCancelable(false);
        mContext = (Activity) context;
    }
}
