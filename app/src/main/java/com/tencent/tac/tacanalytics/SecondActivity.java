package com.tencent.tac.tacanalytics;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tac.analytics.TACAnalyticsService;

/**
 * Created by wjielai on 2017/11/17.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setText("这个页面2s后会自动关闭");

        setContentView(textView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    private void showFeedback(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        TACAnalyticsService.getInstance().trackPageAppear(this, "secondPage");
        showFeedback(String.format("页面 %s 开启", "secondPage"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        TACAnalyticsService.getInstance().trackPageDisappear(this, "secondPage");
        showFeedback(String.format("页面 %s 关闭", "secondPage"));
    }
}
