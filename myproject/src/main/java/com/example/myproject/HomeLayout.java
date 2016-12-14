package com.example.myproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

/**
 * Created by Administrator on 2016/11/27.
 */

public class HomeLayout extends LinearLayout {
    MaterialCalendarView calendarView;
    Scroller mScroller;

    public HomeLayout(Context context) {
        super(context);
    }

    public HomeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            init();
        }
    }

    private void init() {
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        mScroller = new Scroller(calendarView.getContext());
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.e(">>>onScrollChanged>>>>>>>l=" + l + " t" + t + " oldl" + oldl + " oldt" + oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        boolean canTouched = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mInterceptLastY;
                int deltaX = x - mInterceptLastX;
                if(Math.abs(deltaX) < Math.abs(deltaY)){
                    int dScrollY = mInterceptLastY - y;
                    mInterceptLastY = y;
                    if (dScrollY<0){// && this.getTop() < calendarView.getBottom() && calendarView.getScrollY() < calendarView.getHeight() - 500) {
                        LogUtils.e(">>>>>>scroll>>>>>up");
                        calendarView.scrollTo(calendarView.getScrollX(), calendarView.getScrollY() + dScrollY);
                    }
                    if (dScrollY>0){// && calendarView.getScrollY() > calendarView.getTop()) {
                        calendarView.scrollTo(calendarView.getScrollX(), calendarView.getScrollY() + dScrollY);
                        LogUtils.e(">>>>>>scroll>>>>>down" + dScrollY);
//                        mScroller.startScroll(calendarView.getScrollX(),calendarView.getScrollY(),0,dScrollY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * onInterceptTouchEvent最后坐标
     */
    private int mInterceptLastX;
    private int mInterceptLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        boolean intercepted = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptLastX = x;
                mInterceptLastY = y;
//                LogUtils.e("onInterceptTouchEvent_ACTION_DOWN>>>>>>" + " mInterceptLastX=" + x + " mInterceptLastY" + y);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mInterceptLastY;
                int deltaX = x - mInterceptLastX;
                if (Math.abs(deltaY) < 10) {
                    //横向移动的时候不拦截touch事件，让viewpager处理
                    intercepted = false;
                } else if(Math.abs(deltaX) < Math.abs(deltaY)){
                    intercepted = true;
//                    LogUtils.e("mInterceptLastY-y>>>>>>" + (mInterceptLastY - y));
//                    int dScrollY = mInterceptLastY - y;
//                    mInterceptLastY = y;
//                    if (dScrollY<0){// && this.getTop() < calendarView.getBottom() && calendarView.getScrollY() < calendarView.getHeight() - 500) {
//                        LogUtils.e(">>>>>>scroll>>>>>up");
//                        calendarView.scrollTo(calendarView.getScrollX(), calendarView.getScrollY() + dScrollY);
//                    }
//                    if (dScrollY>0){// && calendarView.getScrollY() > calendarView.getTop()) {
//                        calendarView.scrollTo(calendarView.getScrollX(), calendarView.getScrollY() + dScrollY);
//                        LogUtils.e(">>>>>>scroll>>>>>down" + dScrollY);
////                        mScroller.startScroll(calendarView.getScrollX(),calendarView.getScrollY(),0,dScrollY);
//                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
//                LogUtils.e("onInterceptTouchEvent_ACTION_UP>>>>>>"+" x="+x+" y"+y);
                break;
        }
//        LogUtils.e(">>>onInterceptTouchEvent>>>>>>>calendarView.getTop()="+calendarView.getTop()+" calendarView.getBottom()="+calendarView.getBottom());

//        calendarView.state().edit()
//                .setCalendarDisplayMode(CalendarMode.WEEKS)
//                .commit();
//        LogUtils.e(">>>onInterceptTouchEvent>>>>>>>calendarView.getTop()="+calendarView.getTop()+" calendarView.getBottom()="+calendarView.getBottom());

        return intercepted;
    }
}
