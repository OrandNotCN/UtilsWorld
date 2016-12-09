package com.orandnot.recycletimepageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.rd.PageIndicatorView;
import com.rd.animation.AnimationType;

import java.util.List;

/**
 * Created by orandnot on 2016/12/8.
 */
public class RecycleTimePageView extends RelativeLayout{
    private ViewPager pager;
    private RecycleTimePageAdptar mAdpter;
    private boolean isAutoPlay;
    private boolean isTouching;
    private int intervalTime;
    public RecycleTimePageView(Context context) {
        super(context);
    }

    public RecycleTimePageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
    }

    public RecycleTimePageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
    }

    private void initAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RecycleTimePageView);
        this.isAutoPlay = typedArray.getBoolean(R.styleable.RecycleTimePageView_rtpv_auto,false);
        this.intervalTime=typedArray.getInt(R.styleable.RecycleTimePageView_rtpv_interval_time,3);
    }


    public void initUI(List list) {
        this.removeAllViews();
        pager = new ViewPager(getContext());
        mAdpter = new RecycleTimePageAdptar(getContext(),list);
        pager.setAdapter(mAdpter);
        PageIndicatorView indicatorView = new PageIndicatorView(getContext());
        LayoutParams params = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,5,5,5);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorView.setLayoutParams(params);
        indicatorView.setRadius(3);
        indicatorView.setPadding(5);
        indicatorView.setAnimationType(AnimationType.THIN_WORM);
        indicatorView.setInteractiveAnimation(true);
        indicatorView.setViewPager(pager);
        indicatorView.setSelectedColor(Color.GREEN);
        indicatorView.setUnselectedColor(Color.WHITE);
        this.addView(pager,new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        this.addView(indicatorView);
    }

    /**
    *设置间隔时间
     * 一旦设置默认时间表示
     */
    public RecycleTimePageView setIntervalTime(int intervalTime){
        this.intervalTime = intervalTime;
        return this;
    }

    public void startAutoPlay(){
        handler.sendEmptyMessageDelayed(0,intervalTime*1000);
    }

    public void setAutoPlay(boolean flag){
        isAutoPlay = flag;
    }

    public void setListener(RecycleTimePageAdptar.OnPageClickListener listener){
        mAdpter.setListener(listener);
    }

    public class OnPageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(isAutoPlay && !isTouching) {
                        pager.setCurrentItem((pager.getCurrentItem() + 1) % 5);
                        handler.sendEmptyMessageDelayed(0,intervalTime*1000);
                    }
                    break;
            }
        }
    };

    //处理触摸事件时，不进行轮播
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouching = true;
                break;
            case MotionEvent.ACTION_UP:
                isTouching=false;
                handler.removeMessages(0);//清空之前的消息
                handler.sendEmptyMessageDelayed(0,intervalTime*1000);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
