package com.zlc.utils.activity;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

//JavaScript代码：
//
// function call(){
//    test.callFromJs();
// }
// <img src="img/refreshicon.png" onclick="call()" alt="重新刷新" />
class JsCall {
    private Context mContext;
    public JsCall(Context context){
        this.mContext = context;
    }

     //在js中被调用的方法
    @JavascriptInterface
    public void callFromJs(){
        Toast.makeText(mContext, "js调用啦~", Toast.LENGTH_LONG).show();
    }
}