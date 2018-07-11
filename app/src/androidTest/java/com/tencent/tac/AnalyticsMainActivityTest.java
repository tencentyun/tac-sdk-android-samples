package com.tencent.tac;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tencent.tac.R;
import com.tencent.tac.analytics.TACAnalyticsService;
import com.tencent.tac.tacanalytics.AnalyticsMainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * </p>
 * Created by wjielai on 2018/7/2.
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AnalyticsMainActivityTest {

    @Rule
    public ActivityTestRule<AnalyticsMainActivity> mActivityRule = new ActivityTestRule<>(
            AnalyticsMainActivity.class);

    @Before
    public void setup() {

    }

    @Test
    public void testButtonClick() throws InterruptedException{
        assertTrue(TACAnalyticsService.getInstance().isActive());

        onView(withId(R.id.btn_customevent_args_count))
                .perform(click());
        onView(withId(R.id.btn_customevent_kv_count))
                .perform(click());
        onView(withId(R.id.btn_duration_event))
                .perform(click());
        onView(withId(R.id.btn_customevent_kv_duration_begin))
                .perform(click());
        onView(withId(R.id.btn_customevent_kv_duration_end))
                .perform(click());
        onView(withId(R.id.custom_properties))
                .perform(click());
        onView(withId(R.id.btn_new_session))
                .perform(click());

        onView(withId(R.id.btn_track_page))
                .perform(click());
        Thread.sleep(3000);

        onView(withId(R.id.track_webview_event))
                .perform(click());
        Thread.sleep(2000);
    }
}
