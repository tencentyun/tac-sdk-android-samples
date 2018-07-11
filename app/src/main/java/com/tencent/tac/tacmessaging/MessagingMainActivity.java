package com.tencent.tac.tacmessaging;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.tac.R;
import com.tencent.tac.messaging.TACMessagingLocalMessage;
import com.tencent.tac.messaging.TACMessagingNotificationBuilder;
import com.tencent.tac.messaging.TACMessagingService;
import com.tencent.tac.messaging.type.NotificationActionType;
import com.tencent.tac.messaging.type.NotificationTime;

//import com.tencent.tac.messaging.type.MessageType;

/**
 * <p>
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */

public class MessagingMainActivity extends AppCompatActivity {

    private EditText builderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging_activity_main);
        builderId = findViewById(R.id.builder_id);
    }

    public void onStartMessaging(View view) {
        TACMessagingService.getInstance().start(this);
    }

    public void onStopMessaging(View view) {
        TACMessagingService.getInstance().stop(this);
    }

    public void onGetToken(View view) {

        String token = TACMessagingService.getInstance().getToken().getTokenString();
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        Log.d("messaging", "token is " + token);

        ApplicationInfo appInfo = null;

        Context context = getApplicationContext();
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        appInfo.metaData.putString("test1", "value1");

        Log.d("huaweitest", "context = " + context);
        Log.i("huaweitest", "package name = " + context.getPackageName());
        Log.i("huaweitest", "app info " + appInfo);
        Log.i("huaweitest", "app info meta data " + appInfo.metaData);

        appInfo.metaData.putString("test2", "value2");

        //Context context = getApplicationContext();
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("huaweitest", "context = " + context);
        Log.i("huaweitest", "package name = " + context.getPackageName());
        Log.i("huaweitest", "app info " + appInfo);
        Log.i("huaweitest", "app info meta data " + appInfo.metaData);
    }


    public void onCreateLocalNotification(View view) {

        TACMessagingLocalMessage localMessage = new TACMessagingLocalMessage();
        localMessage.setTitle("一条本地通知");
        localMessage.setContent("具体的通知内容知内容内容容");
        //localMessage.setType(MessageType.NOTIFICATION);
        localMessage.setNotificationTime(new NotificationTime.NotificationTimeBuilder()
                //.setHourAndMinute(16,50)
                .build());
        String res = getResources().getResourceName(R.mipmap.ic_launcher_round);
        localMessage.setIconRes(res);
        localMessage.setActionType(NotificationActionType.ACTION_OPEN_BROWSER);
        localMessage.setActivity("com.tencent.tac.MainActivity");

        Log.d("messaging", "add local notification" + TACMessagingService.getInstance().addLocalNotification(this, localMessage));
    }

    public void onAddNotificationBuilder(View view) {

        String builderIdString = builderId.getText().toString();
        int builderIdInt = -1;
        try {
            builderIdInt = Integer.parseInt(builderIdString);
        } catch (NumberFormatException exception) {
            Toast.makeText(this, "样式 id 必须是正整数", Toast.LENGTH_SHORT).show();
        }
        if (builderIdInt > 0) {
            TACMessagingNotificationBuilder notificationBuilder = new TACMessagingNotificationBuilder()
                    .setNotificationLargeIcon(R.mipmap.ic_launcher_round);
            TACMessagingService.getInstance().addNotificationBuilder(this, builderIdInt, notificationBuilder);
            Toast.makeText(this, "设置成功，您可以在控制台设置通知样式为 " + builderIdInt, Toast.LENGTH_SHORT).show();
        }

    }


}
