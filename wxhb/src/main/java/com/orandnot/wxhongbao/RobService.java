package com.orandnot.wxhongbao;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class RobService extends AccessibilityService {
    private static final int NOTIFICATION_ID = 1001;

    private AccessibilityNodeInfo lastRPNode;

    private boolean isGet = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.app_icon);
        builder.setTicker("微信红包插件开始运行...");
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText("微信红包插件正在运行...");
        Notification notification = builder.build();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                handleNotification(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                String className = event.getClassName().toString();
                LogUtils.e(className);
                if (!isGet && className.equals("com.tencent.mm.ui.LauncherUI")) {
                    getPacket();
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
                    openPacket();
                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
                    if(!isGet)
                    close();
                    isGet = true;
                    goToHome();

                }

                break;
        }
    }

    private void goToHome() {
//        Intent home = new Intent(Intent.ACTION_MAIN);
//        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        home.addCategory(Intent.CATEGORY_HOME);
//        startActivity(home);
    }

    /**
     * 处理通知栏信息
     * <p>
     * 如果是微信红包的提示信息,则模拟点击
     *
     * @param event
     */
    private void handleNotification(AccessibilityEvent event) {
        List<CharSequence> texts = event.getText();
        if (!texts.isEmpty()) {
            for (CharSequence text : texts) {
                String content = text.toString();
                //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                if (content.contains("[微信红包]")) {
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                        isGet =false;
                        Notification notification = (Notification) event.getParcelableData();
                        PendingIntent pendingIntent = notification.contentIntent;
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                        CommonUtils.playMusic(this);
                        CommonUtils.shark(this);
                    }
                }
            }
        }
    }

    /**
     * 关闭红包详情界面,实现自动返回聊天窗口
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void close() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了关闭按钮的id
            String closeId = "com.tencent.mm:id/" + CommonUtils.getRPBack(this);
            LogUtils.e("------------closing-----" + closeId);
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId(closeId);
            LogUtils.e("------------closing-----" + infos.toString());
            nodeInfo.recycle();
            for (AccessibilityNodeInfo item : infos) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                LogUtils.e("------------close-----");
            }
        }
    }

    /**
     * 模拟点击,拆开红包
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了红包控件的id
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/" + CommonUtils.getRPId(this));
            nodeInfo.recycle();
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 模拟点击,打开抢红包界面
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        recycle(rootNode);
        while (lastRPNode != null && lastRPNode.getParent() != null) {
            lastRPNode = lastRPNode.getParent();
            if (lastRPNode.isClickable()) {
                lastRPNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
        }

    }


    /**
     * 递归查找当前聊天窗口中的红包信息
     * <p>
     * 聊天窗口中的红包都存在"领取红包"一词,因此可根据该词查找红包
     *
     * @param node
     */
    public void recycle(AccessibilityNodeInfo node) {
        if (node.getChildCount() == 0) {
            if (node.getText() != null) {
                if ("领取红包".equals(node.getText().toString())) {
                    lastRPNode = node;
                }
            }
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                if (node.getChild(i) != null) {
                    recycle(node.getChild(i));
                }
            }
        }
    }


    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, RobService.class));
    }
}