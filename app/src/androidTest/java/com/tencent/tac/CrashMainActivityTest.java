package com.tencent.tac;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tencent.tac.crash.TACCrashService;
import com.tencent.tac.messaging.TACMessagingService;
import com.tencent.tac.taccrash.CrashMainActivity;
import com.tencent.tac.tacmessaging.MessagingMainActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * </p>
 * Created by wjielai on 2018/7/3.
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CrashMainActivityTest {

    @Rule
    public ActivityTestRule<CrashMainActivity> mActivityRule = new ActivityTestRule<>(
            CrashMainActivity.class);

    @Test
    public void testService() {
        Assert.assertTrue(TACCrashService.getInstance().isActive());
    }
}
