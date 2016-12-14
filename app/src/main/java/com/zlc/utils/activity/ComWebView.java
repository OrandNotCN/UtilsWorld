package com.zlc.utils.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zlc.utils.LogUtils;

/**
 * Created by Administrator on 2016/12/14.
 */
public class ComWebView extends WebView{
    public ComWebView(Context context) {
        super(context);
        initSetting(context);
    }

    public ComWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSetting(context);
    }

    public ComWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSetting(context);
    }

    private void initSetting(final Context context) {
        WebSettings settings =getSettings();
        settings.setUseWideViewPort(true);//适配屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);//支持放大缩小
        settings.setDisplayZoomControls(false);//隐藏放大缩小的按钮
        settings.setDomStorageEnabled(true);//支持Html5标签
        settings.setJavaScriptEnabled(true);//支持javascrip
//        addJavascriptInterface(new JsCall(context), "test");//和JavaScript交互,CalledByJs是被javascript调用的类

        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new MyWebViewChromeClient());
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimetype, long contentLength) {
                //下载的url,调用浏览器下载
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
            }


        });
    }


    class MyWebViewChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            LogUtils.i("网页标题：" + title);
        }

    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //在这里进行加载错误、没有网络情况的处理
            //方式1：加载一个本地的默认页面
            //webView.loadData("file:///android_asset/refresh/refresh.html");
            //方式2：显示一个默认布局
            setVisibility(View.GONE);
        }

        // 自定义拦截
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url!=null && !"".equals(url)){
                if("open_login".equals(url)){

                    return true;
                }else {
                    return false;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
