package com.orandnot.wxhongbao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by orandnot on 2017/1/17.
 * 用于接收手机广播，启动红包服务类
 * 增加监听生命周期几率
 */
public class BaseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.saveErrorLog(intent.getAction());
        if(!CommonUtils.isServiceWork(context,RobService.class.getName().toString())){
            context.startService(new Intent(context,RobService.class));
        }
    }
}
