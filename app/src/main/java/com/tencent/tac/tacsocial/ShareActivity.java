package com.tencent.tac.tacsocial;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.tencent.tac.R;
import com.tencent.tac.TACApplication;
import com.tencent.tac.social.share.BitmapObject;
import com.tencent.tac.social.share.FileObject;
import com.tencent.tac.social.share.ObjectMetadata;
import com.tencent.tac.social.share.ShareResult;
import com.tencent.tac.social.share.TACShareDialog;
import com.tencent.tac.social.share.UrlObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShareActivity extends Activity {

    TACShareDialog shareDialog = new TACShareDialog();

    Bitmap musicAlbum;
    Bitmap videoAlbum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share);

        TACApplication.configure(this);

        musicAlbum = BitmapFactory.decodeResource(getResources(), R.drawable.music_album);
        videoAlbum = BitmapFactory.decodeResource(getResources(), R.drawable.video_thumb);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ShareResult shareResult = shareDialog.getQQShareResult(requestCode, resultCode, data);
        if (shareResult != null) {
            Toast.makeText(this, "qq share result : " + shareResult.getResult(), Toast.LENGTH_LONG).show();
        }
    }

    public void shareWebPage(View view) {
        UrlObject urlObject = new UrlObject("http://www.qq.com");
        urlObject.setMetadata(new ObjectMetadata.Builder().title("QQ").description("QQ HomePage").build());

        shareDialog.shareUrl(this, urlObject);
    }

    public void shareText(View view) {
        shareDialog.sharePlainText(this, "this is a plain text");
    }

    public void shareBitmap(View view) {
        shareDialog.shareBitmap(this, new BitmapObject(musicAlbum));
    }

    public void shareMusic(View view) {
        UrlObject urlObject = new UrlObject("http://music.163.com/#/share/2095551/3510133339");
        urlObject.setMetadata(new ObjectMetadata.Builder().audio()
                .thumb(musicAlbum)
                .thumbUrl("http://p1.music.126.net/0SDC5efp1iJZ6g9Jw1fcnQ==/793847395278362.jpg?param=130y130")
                .title("あやとり")
                .description("諫山実生")
                .build());

        shareDialog.shareUrl(this, urlObject);
    }

    public void shareVideo(View view) {
        UrlObject urlObject = new UrlObject("https://www.bilibili.com/video/av21061574/");
        urlObject.setMetadata(new ObjectMetadata.Builder().video()
                .thumb(videoAlbum)
                .thumbUrl("https://wx4.sinaimg.cn/orj360/62f68aebly1fptxp9vh1zj20qo0gotat.jpg")
                .title("【面筋哥×波澜哥】我的烤面筋，融化你的心！")
                .build());

        shareDialog.shareUrl(this, urlObject);
    }

    public void sharePhotoFile(View view) {
        File photoFile = getMusicPhotoFile();
        FileObject fileObject = new FileObject(photoFile.getPath());
        fileObject.setMetadata(new ObjectMetadata.Builder().image().build());

        shareDialog.shareFile(this, fileObject);
    }

    public void shareVideoFile(View view) {
        File videoFile = getVideoFile();
        FileObject fileObject = new FileObject(videoFile.getPath());
        fileObject.setMetadata(new ObjectMetadata.Builder().video().build());
        shareDialog.shareFile(this, fileObject);
    }

    public void shareMultiPhotoFiles(View view) {
        List<FileObject> files = new ArrayList<>(2);
        files.add(new FileObject(getMusicPhotoFile().getPath()));
        files.add(new FileObject(getVideoPhotoFiles().getPath()));
        ObjectMetadata metadata = new ObjectMetadata.Builder().image().build();

        shareDialog.shareMultiFiles(this, files, metadata);
    }

    public void shareMultiVideoFiles(View view) {
        List<FileObject> files = new ArrayList<>(2);
        files.add(new FileObject(getVideoFile().getPath()));
        files.add(new FileObject(getVideoFile().getPath()));
        ObjectMetadata metadata = new ObjectMetadata.Builder().video().build();

        shareDialog.shareMultiFiles(this, files, metadata);
    }

    public void shareFile(View view) {
        File file = getRawFile();
        shareDialog.shareFile(this, new FileObject(file.getPath()));
    }

    public void shareMultiFiles(View view) {
        List<FileObject> files = new ArrayList<>(2);
        files.add(new FileObject(getRawFile().getPath()));
        files.add(new FileObject(getRawFile().getPath()));
        ObjectMetadata metadata = new ObjectMetadata.Builder().mimeType("text/plain").build();

        shareDialog.shareMultiFiles(this, files, metadata);
    }

    private File getRawFile() {
        // 产生临时分享文件
        // 测试使用，请不要再主线程写文件
        File file = new File(getExternalCacheDir(), "raw_text.txt");
        if (!file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] data = new byte[1000];
                fileOutputStream.write(data);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    private File getVideoPhotoFiles() {
        // 产生临时分享文件
        // 测试使用，请不要再主线程写文件
        File file = new File(getExternalCacheDir(), "video_album.jpg");
        if (!file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                videoAlbum.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    private File getMusicPhotoFile() {
        // 产生临时分享文件
        // 测试使用，请不要再主线程写文件
        File file = new File(getExternalCacheDir(), "music_album.jpg");
        if (!file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                musicAlbum.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    private File getVideoFile() {
        // 产生临时分享文件
        // 测试使用，请不要再主线程写文件
        File file = new File(getExternalCacheDir(), "video.mp4");
        if (!file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                InputStream inputStream = getResources().openRawResource(R.raw.overlay_mask);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                byte[] data = new byte[2048];
                int available;
                while ((available = bufferedInputStream.read(data)) != -1) {
                    fileOutputStream.write(data, 0, available);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}
