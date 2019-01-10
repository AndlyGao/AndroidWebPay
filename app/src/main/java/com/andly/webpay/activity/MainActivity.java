package com.andly.webpay.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andly.webpay.R;
import com.andly.webpay.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andly on 2017/9/26.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bt_alipay)
    Button mBtAlipay;
    @BindView(R.id.bt_wechatpay)
    Button mBtWechatpay;
    private String mAliPayUrl = "";//支付宝网页支付url
    private String mWechatUrl = "";//微信网页支付url
    public static String notifyUrl = "";//支付回调url
    public static String refererUrl = "";//微信referer url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @butterknife.OnClick({R.id.bt_alipay, R.id.bt_wechatpay})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_alipay:

                aliH5Pay(mAliPayUrl);
                break;
            case R.id.bt_wechatpay:

                weixinWapPay(mWechatUrl);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 100) {
                ToastUtils.showLongToast(this, "取消支付");
            } else {
                ToastUtils.showLongToast(this, "支付完毕进行订单校验");
            }
            // 处理回调信息
        }
    }

    private void weixinWapPay(String payUrl) {
        if (!isWeixinAvilible()) {
            ToastUtils.showLongToast(this, "请预先安装微信");
            return;
        }
        Intent intent = new Intent(this, WeinxinPayH5Activity.class);
        intent.putExtra("payUrl", payUrl);
        startActivityForResult(intent, 1);
    }

    private void aliH5Pay(String payUrl) {
        Intent intent = new Intent(this, AlipayH5Activity.class);
        intent.putExtra("payUrl", payUrl);
        startActivityForResult(intent, 1);
    }

    /**
     * 判断是否安装的微信
     */
    public boolean isWeixinAvilible() {
        final PackageManager packageManager = getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    Log.d("andly", "已安装了微信");
                    return true;
                }
            }
        }
        Log.d("andly", "未安装微信");
        return false;
    }
}
