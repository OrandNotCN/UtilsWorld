package com.orandnot.dispatcheventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/12/12.
 */
public class Sub1RelativeLayout extends RelativeLayout{
    public Sub1RelativeLayout(Context context) {
        super(context);
    }

    public Sub1RelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sub1RelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public  String TAG = getClass().getName();
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
    }

}
