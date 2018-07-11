package com.tencent.tac;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tencent.tac.messaging.TACMessagingService;
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
public class MessagingMainActivityTest {

    @Rule
    public ActivityTestRule<MessagingMainActivity> mActivityRule = new ActivityTestRule<>(
            MessagingMainActivity.class);

    @Test
    public void testService() {
        Assert.assertTrue(TACMessagingService.getInstance().isActive());
        Assert.assertNotNull(TACMessagingService.getInstance().getToken().getTokenString());
    }
}
