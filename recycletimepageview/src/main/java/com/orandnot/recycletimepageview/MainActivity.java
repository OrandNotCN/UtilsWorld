package com.orandnot.recycletimepageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecycleTimePageAdptar.OnPageClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        RecycleTimePageView rtPageView = (RecycleTimePageView) findViewById(R.id.rtPageView);
        List list = new ArrayList<>();
        int[] imgId = new int[]{R.mipmap.img_0, R.mipmap.img_1, R.mipmap.img_2, R.mipmap.img_3, R.mipmap.img_4};
        for (int id : imgId) {
            ImageView view = new ImageView(this);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setImageResource(id);
            list.add(view);
        }
        rtPageView.initUI(list);
        rtPageView.startAutoPlay();
        rtPageView.setListener(this);
    }

    @Override
    public void onPagePosition(int position) {
        Log.i(">>>>>>>>>>>pageClick",""+position);
    }
}
