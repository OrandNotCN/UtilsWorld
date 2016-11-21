package com.zlc.utils.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zlc.utils.LogUtils;
import com.zlc.utils.R;

import java.io.File;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView)findViewById(R.id.webView);
        initConfig();
        initLoad();
    }

    private void initConfig() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);//适配屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);//支持放大缩小
        settings.setDisplayZoomControls(false);//隐藏放大缩小的按钮
        settings.setDomStorageEnabled(true);//支持Html5标签
        settings.setJavaScriptEnabled(true);//支持javascrip
        webView.addJavascriptInterface(new JsCall(this), "test");//和JavaScript交互,CalledByJs是被javascript调用的类

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebViewChromeClient());
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimetype, long contentLength) {
                //下载的url,调用浏览器下载
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            }


        });

    }


    class MyWebViewChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
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
            findViewById(R.id.tvError).setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
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


    private void initLoad() {
        webView.loadUrl("http://blog.csdn.net/p522947409/article/details/53271525");//加载网络网页
//        wvTest.loadData("file:///android_asset/refresh/refresh.html");//加载本地网页
//        webView.reload();//刷新当前页面
    }

    /**
     * WebView清空缓存
     */
    public static void clearCache(Context context) {
        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        context.deleteDatabase("webview.db");
        context.deleteDatabase("webviewCache.db");
        context.deleteDatabase("webviewCookiesChromium.db");
        context.deleteDatabase("webviewCookiesChromiumPrivate.db");
        //WebView 缓存文件
        File webviewCacheDir = new File(context.getCacheDir().getAbsolutePath()+"/webviewCacheChromium");
        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
//            deleteFile(webviewCacheDir);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(webView.canGoBack()){//返回上个页面
            webView.goBack();
        }else{
            finish();
        }

//        if(webView.canGoForward()){//去刚才浏览的页面
//            webView.goForward();
//        }
    }
}
