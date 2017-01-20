package com.orandnot.wxhongbao;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity {
    Switch swAu;
    Switch sws;
    Switch swv;
    Spinner spd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onResume() {
        super.onResume();

        swAu.setChecked(RobService.isAuto);
        sws.setChecked(RobService.isShake);
        swv.setChecked(RobService.isVoice);
        spd.setSelection(RobService.delay);
        TextView tapp = (TextView) findViewById(R.id.app);
        if (CommonUtils.isHaveVersion(this)) {
            tapp.setText("当前微信版本号：" + CommonUtils.getVersion(this, "com.tencent.mm"));
            if (!CommonUtils.isAccessibilitySettingsOn(this, RobService.class)) {
                showDialogTip();
            }
        } else {
            findViewById(R.id.rls).setVisibility(View.GONE);
            tapp.setText("不支持当前微信版本" + CommonUtils.getVersion(this, "com.tencent.mm"));
        }

    }

    private void init() {
        swAu = (Switch) findViewById(R.id.swAu);
        swAu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                RobService.isAuto = isChecked;
            }
        });

        sws = (Switch) findViewById(R.id.sws);
        sws.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                RobService.isShake = isChecked;
            }
        });

        swv = (Switch) findViewById(R.id.swv);
        swv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                RobService.isVoice = isChecked;
            }
        });

        spd = (Spinner) findViewById(R.id.spde);
        spd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                RobService.delay = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


    }

    private void showDialogTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置参数
        builder.setTitle("未设置辅助服务")
                .setMessage("请先开启辅助服务")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {// 积极
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                finish();
            }
        });
        builder.create().show();
    }
}
