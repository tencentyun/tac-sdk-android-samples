package com.tencent.tac;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * <p>
 * </p>
 * Created by wjielai on 2018/6/28.
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */
public class NotOfficialDialog extends AppCompatDialogFragment {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle(R.string.please_use_official_app_title)
                .setMessage(R.string.please_use_official_app_message)
                .setPositiveButton(R.string.please_go_to_download_official_app, (dialog, which) -> {
                    gotoDownload();
                    dismiss();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    dismiss();
                })
                .setCancelable(true)
                .create();
    }

    private void gotoDownload() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://tac-android-libs-1253960454.file.myqcloud.com/tac-sample-spec.apk"));
        startActivity(intent);
    }
}
