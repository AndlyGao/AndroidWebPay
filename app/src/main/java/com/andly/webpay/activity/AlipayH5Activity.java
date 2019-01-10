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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static com.andly.webpay.activity.MainActivity.notifyUrl;

/**
 * Created by andly on 2017/9/26.
 */
public class AlipayH5Activity extends Activity {

    private WebView webview;
    private String TAG = "AlipayH5Activity";
    private boolean ispay = true;
    private boolean isDo = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ispay = true;
        View view = this.getRootView();
        RelativeLayout.LayoutParams rootlp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(view, rootlp);
        String payUrl = getIntent().getStringExtra("payUrl");
        //应用过程中将其隐藏掉效果更佳
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
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "访问的url地址：" + url);
                if (parseScheme(url)) {
                    if (ispay) {
                        ispay = false;
                        setResult(101);
                    }
                    finish();
                    Log.i("andly", "parseScheme");
                }
                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                    }
                    return true;
                }
                if (url.startsWith("https://mclient.alipay.com/h5Continue.htm?")) {
                    if (!isDo) {
                        isDo = true;
                        return true;
                    }
                }
                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(payUrl);
        sb.append("</html>");
        webview.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);

    }

    public boolean parseScheme(String url) {
        if (url.contains(notifyUrl)) {
            return true;
        } else {
            return false;
        }
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
            Log.i("andly", "onKeyDown" + keyCode);
            if (ispay) {
                ispay = false;
                setResult(101);
            } else {
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("andly", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ispay) {
            ispay = false;
            setResult(101);
        }
        Log.i("andly", "onDestroy");
    }
}
