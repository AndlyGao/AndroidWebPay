package com.andly.webpay.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.andly.webpay.utils.ToastUtils;
import com.andly.webpay.view.CheckDialog;

import java.util.HashMap;
import java.util.Map;

import static com.andly.webpay.activity.MainActivity.refererUrl;

/**
 * Created by andly on 2017/9/26.
 */
public class WeinxinPayH5Activity extends Activity {
    private WebView webview;
    private String TAG = "WeinxinPayH5Activity";
    private boolean ispay = false;
    private String payUrl;
    private CheckDialog checkOrderDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ispay = false;
        View view = this.getRootView();
        RelativeLayout.LayoutParams rootlp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(view, rootlp);
        payUrl = getIntent().getStringExtra("payUrl");

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webSettings.setDatabaseEnabled(true);
        // 使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "访问的url地址：" + url);
                // 如下方案可在非微信内部WebView的H5页面中调出微信支付
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                ToastUtils.showLongToast(WeinxinPayH5Activity.this, "抱歉，创建支付订单失败，请稍后再试！");

            }
        });

        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Referer", refererUrl);
        webview.loadUrl(payUrl, extraHeaders);
    }


    private View getRootView() {
        LinearLayout view = new LinearLayout(this);
        view.setBackgroundColor(Color.parseColor("#ffffff"));
        webview = new WebView(this);
        LinearLayout.LayoutParams lpaarams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lpaarams.gravity = Gravity.CENTER;
        lpaarams.topMargin = 0;
        view.addView(webview, lpaarams);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setPadding(0, 0, 0, 0);
        return view;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(100);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkOrderDialog == null) {
            checkOrderDialog = new CheckDialog(this);
        } else {
            if (checkOrderDialog.isShowing()) {
                checkOrderDialog.dismiss();
                checkOrderDialog.show();
            } else {
                checkOrderDialog.show();
            }
        }
        if (ispay) {
            ispay = false;
            setResult(101);
            finish();
        }
        ispay = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (checkOrderDialog != null && checkOrderDialog.isShowing()) {
            checkOrderDialog.dismiss();
            checkOrderDialog.show();
        }
    }
}
