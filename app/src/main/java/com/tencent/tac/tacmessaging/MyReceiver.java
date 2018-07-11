package com.tencent.tac.tacmessaging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tac.messaging.TACMessagingReceiver;
import com.tencent.tac.messaging.TACMessagingText;
import com.tencent.tac.messaging.TACMessagingToken;
import com.tencent.tac.messaging.TACNotification;
import com.tencent.tac.messaging.type.PushChannel;

/**
 * <p>
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */

public class MyReceiver  extends TACMessagingReceiver {

    @Override
    public void onRegisterResult(Context context, int errorCode, TACMessagingToken token) {
        if (errorCode == 0) {
            Toast.makeText(context, "推送服务启动成功，token 是 " + token.getTokenString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "推送服务启动失败，错误码是 " + errorCode, Toast.LENGTH_SHORT).show();
        }
        Log.i("messaging", "MyReceiver::OnRegisterResult : code is " + errorCode + ", token is " + token.getTokenString());
    }

    @Override
    public void onUnregisterResult(Context context, int code) {
        if (code == 0) {
            Toast.makeText(context, "停止成功 ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "停止失败，错误码是 " + code, Toast.LENGTH_SHORT).show();
        }
        Log.i("messaging", "MyReceiver::onUnregisterResult : code is " + code);
    }

    @Override
    public void onMessageArrived(Context context, TACMessagingText tacMessagingText, PushChannel channel) {
        Toast.makeText(context, "收到透传消息：" + tacMessagingText, Toast.LENGTH_LONG).show();
        Log.i("messaging", "MyReceiver::OnTextMessage : message is " + tacMessagingText+ " pushChannel " + channel);
    }

    @Override
    public void onNotificationArrived(Context context, TACNotification tacNotification, PushChannel pushChannel) {
        Toast.makeText(context, "收到通知消息：" + pushChannel, Toast.LENGTH_LONG).show();
        Log.i("messaging", "MyReceiver::onNotificationArrived : notification is " + tacNotification + " pushChannel " + pushChannel);

    }

    @Override
    public void onNotificationClicked(Context context, TACNotification tacNotification, PushChannel pushChannel) {
        Toast.makeText(context, "通知被点击：" + tacNotification, Toast.LENGTH_LONG).show();
        Log.i("messaging", "MyReceiver::onNotificationClicked : notification is " + tacNotification + " pushChannel " + pushChannel);

    }

    @Override
    public void onNotificationDeleted(Context context, TACNotification tacNotification, PushChannel pushChannel) {
        Toast.makeText(context, "通知被划掉：" + tacNotification, Toast.LENGTH_LONG).show();
        Log.i("messaging", "MyReceiver::onNotificationDeleted : notification is " + tacNotification + " pushChannel " + pushChannel);

    }

    @Override
    public void onBindTagResult(Context context, int i, String s) {

    }

    @Override
    public void onUnbindTagResult(Context context, int i, String s) {

    }

}
