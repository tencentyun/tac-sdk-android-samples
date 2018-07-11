package com.tencent.tac.taccrash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tencent.tac.R;
import com.tencent.tac.crash.TACCrashLogLevel;
import com.tencent.tac.crash.TACCrashService;
import com.tencent.tac.crash.TACCrashSimulator;

/**
 * <p>
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */

public class CrashMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_activity_main);
    }

    public void onJavaCrashClicked(View view) {

        TACCrashService crashService = TACCrashService.getInstance();
        crashService.setUserSceneTag(this, 9527);
        crashService.putUserData(this, "name", "唐伯虎");
        TACCrashService.getInstance().log(TACCrashLogLevel.INFO, "tac", "自定义日志");
        TACCrashSimulator.testJavaCrash();

    }

    public void onANRCrashClicked(View view) {

        TACCrashSimulator.testANRCrash();
    }

    public void onNativeCrashClicked(View view) {

        TACCrashSimulator.testNativeCrash();
    }


}
