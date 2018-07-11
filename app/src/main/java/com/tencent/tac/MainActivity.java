package com.tencent.tac;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tencent.tac.tacanalytics.AnalyticsMainActivity;
import com.tencent.tac.tacauthorization.AuthMainActivity;
import com.tencent.tac.taccrash.CrashMainActivity;
import com.tencent.tac.tacmessaging.MessagingMainActivity;
import com.tencent.tac.tacpayment.PaymentMainActivity;
import com.tencent.tac.tacsocial.ShareActivity;
import com.tencent.tac.tacstorage.StorageActivity;

/**
 * <p>
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tac_activity_main);
        requestPermissions();
    }

    public void onCrashMainClicked(View view) {
        startActivity(new Intent(this, CrashMainActivity.class));
    }

    public void onAnalyticsClicked(View view) {
        startActivity(new Intent(this, AnalyticsMainActivity.class));
    }

    public void onMessageClicked(View view) {
        startActivity(new Intent(this, MessagingMainActivity.class));
    }

    public void onPaymentClicked(View view) {
        startOfficialActivity(PaymentMainActivity.class);
    }

    public void onStorageClicked(View view) {
        startOfficialActivity(StorageActivity.class);
    }

    public void onAuthorizationClicked(View view) {
        startOfficialActivity(AuthMainActivity.class);
    }

    public void onShareClicked(View view) {
        startOfficialActivity(ShareActivity.class);
    }

    private void startOfficialActivity(Class clazz) {
        boolean isOfficial = "com.tencent.tac.sample".equals(getPackageName());
        if (isOfficial) {
            startActivity(new Intent(this, clazz));
            return;
        }

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.tac.sample", clazz.getName()));
        try {
            startActivity(intent);
            return;
        } catch (ActivityNotFoundException e) {
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        NotOfficialDialog dialog = new NotOfficialDialog();
        dialog.show(ft, "dialog");
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT > 23) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
