package com.orandnot.wxhongbao;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tapp = (TextView) findViewById(R.id.app);
//        int i = R.array.wx6_5_3;//0x7f0c0001
        if(CommonUtils.isHaveVersion(this)) {
            tapp.setText("当前微信版本号：" + CommonUtils.getVersion(this, "com.tencent.mm"));
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }else{
            tapp.setText("不支持当前微信版本" + CommonUtils.getVersion(this, "com.tencent.mm"));
        }
    }
}
