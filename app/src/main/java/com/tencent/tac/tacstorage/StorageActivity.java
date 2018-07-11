package com.tencent.tac.tacstorage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpRequest;
import com.tencent.tac.R;
import com.tencent.tac.TACApplication;
import com.tencent.tac.option.TACApplicationOptions;
import com.tencent.tac.storage.StorageProgressListener;
import com.tencent.tac.storage.StorageResultListener;
import com.tencent.tac.storage.TACStorageOptions;
import com.tencent.tac.storage.TACStorageReference;
import com.tencent.tac.storage.TACStorageService;
import com.tencent.tac.storage.TACStorageTaskSnapshot;

import java.io.File;

/**
 * <p>
 * </p>
 * Created by wjielai on 2017/12/4.
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */

public class StorageActivity extends AppCompatActivity {

    private TACStorageService tacStorageService;

    private TextView fileUriView;
    private Uri pickedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_activity_main);

        fileUriView = findViewById(R.id.file_uri);

        TACApplicationOptions applicationOptions = TACApplication.options();
        TACStorageOptions storageOptions = applicationOptions.sub("storage");
        // 配置签名获取服务器
        storageOptions.setCredentialProvider(new HttpRequest.Builder<String>()
                .scheme("https")
                .host("tac.cloud.tencent.com")
                .path("/client/sts")
                .method("GET")
                .query("bucket", storageOptions.getDefaultBucket())
                .build());

        tacStorageService = TACStorageService.getInstance();

    }

    private void showFeedback(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void pickFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && data != null && data.getData() != null) {
            fileUriView.setText(data.getDataString());
            pickedFile = data.getData();
        }
    }

    public void uploadFile(View view) {
        TACStorageReference reference = tacStorageService.referenceWithPath("/tac_test/tmp");
        showFeedback("开始上传文件到 " + reference.getPath());
        reference.putFile(pickedFile, null).addResultListener(new StorageResultListener<TACStorageTaskSnapshot>() {
            @Override
            public void onSuccess(final TACStorageTaskSnapshot snapshot) {
                showMessage(new Runnable() {
                    @Override
                    public void run() {
                        TACStorageReference ref = snapshot.getStorage();
                        String url = "https://" + ref.getBucket() + ".cos." + ref.getRegion() + ".myqcloud.com" + ref.getPath();
                        Log.d("Storage", "上传成功: " + url);
                        Toast.makeText(StorageActivity.this, "上传成功", Toast.LENGTH_LONG).show();

                        TextView remoteUrlView = findViewById(R.id.remote_file_url);
                        remoteUrlView.setText("远程文件url是：" + url);
                    }
                });
            }

            @Override
            public void onFailure(final TACStorageTaskSnapshot snapshot) {
                showMessage(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StorageActivity.this, "上传失败，" +
                                snapshot.getError(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    public void downloadFile(View view) {
        TACStorageReference reference = tacStorageService.referenceWithPath("/tac_test/tmp");
        Uri fileUri = Uri.fromFile(new File(getExternalCacheDir() + File.separator + "local_tmp"));
        showFeedback("开始下载远程文件： " + reference.getPath());
        reference.downloadToFile(fileUri).addResultListener(new StorageResultListener<TACStorageTaskSnapshot>() {
            @Override
            public void onSuccess(TACStorageTaskSnapshot snapshot) {
                showMessage(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StorageActivity.this, "下载成功", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(final TACStorageTaskSnapshot snapshot) {
                showMessage(new Runnable() {
                    @Override
                    public void run() {
                        if (snapshot.getError() instanceof QCloudServiceException) {
                            QCloudServiceException exception = (QCloudServiceException) snapshot.getError();
                            if (exception.getStatusCode() == 404) {
                                Toast.makeText(StorageActivity.this, "文件不存在。请先点击 '上传文件' 上传",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        Toast.makeText(StorageActivity.this, "下载失败，" +
                                snapshot.getError(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addProgressListener(new StorageProgressListener<TACStorageTaskSnapshot>() {
            @Override
            public void onProgress(TACStorageTaskSnapshot snapshot) {
                Log.i("QCloudStorage", "progress = " + snapshot.getBytesTransferred() + "," +
                        snapshot.getTotalByteCount());
            }
        });
    }

    public void deleteFile(View view) {
        TACStorageReference reference = tacStorageService.referenceWithPath("/tac_test/tmp");
        showFeedback("开始删除远程文件： " + reference.getPath());
        reference.delete().addResultListener(new StorageResultListener<TACStorageTaskSnapshot>() {
            @Override
            public void onSuccess(TACStorageTaskSnapshot snapshot) {
                showMessage(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StorageActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(final TACStorageTaskSnapshot snapshot) {
                showMessage(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(StorageActivity.this, "删除失败，" +
                                snapshot.getError(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void showMessage(Runnable runnable) {
        runOnUiThread(runnable);
    }
}
