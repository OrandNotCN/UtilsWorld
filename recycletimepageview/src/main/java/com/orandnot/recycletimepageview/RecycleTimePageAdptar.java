package com.orandnot.recycletimepageview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by orandnot on 2016/12/8.
 */
public class RecycleTimePageAdptar extends PagerAdapter{

    interface OnPageClickListener{
        void onPagePosition(int position);
    }
    public OnPageClickListener listener;

    public void setListener(OnPageClickListener listener) {
        this.listener = listener;
    }

    private List imgList;
    private Context context;
    public RecycleTimePageAdptar(Context context,List imgList){
        this.imgList = imgList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = (View) imgList.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPagePosition(position);
            }
        });
        container.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }
}
