package com.orandnot.wxhongbao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tapp = (TextView)findViewById(R.id.app);
        tapp.setText(CommonUtils.getVersion(this,"com.tencent.mm"));
    }
}
