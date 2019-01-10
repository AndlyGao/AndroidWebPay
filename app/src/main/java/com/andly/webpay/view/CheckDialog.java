package com.andly.webpay.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.andly.webpay.utils.ScreenUtil;

/**
 * Created by andly on 2017/9/26.
 */
public class CheckDialog extends BaseDialog {


    public boolean isCancel = false;
    private ProgressBar mProgressBar;

    public CheckDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getRootView();
        RelativeLayout.LayoutParams rootlp = new RelativeLayout.LayoutParams(ScreenUtil.dip2px(mContext, 60), ScreenUtil.dip2px(mContext, 75));
        setContentView(view, rootlp);
        mProgressBar.setVisibility(View.VISIBLE);
        setCancelable(false);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        isCancel = flag;
    }


    public void close() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
    }

    public void check() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }


    private View getRootView() {
        mProgressBar = new ProgressBar(mContext);
        RelativeLayout.LayoutParams pblp = new RelativeLayout.LayoutParams(ScreenUtil.dip2px(mContext, 30), ScreenUtil.dip2px(mContext, 30));
        pblp.addRule(RelativeLayout.CENTER_IN_PARENT);
        pblp.topMargin = 100;
        RelativeLayout root = new RelativeLayout(mContext);
        root.setBackgroundDrawable(ScreenUtil.getBackDrawwable(mContext));
        root.addView(mProgressBar, pblp);
        return root;
    }

}
