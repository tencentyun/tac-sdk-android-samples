package com.tencent.tac.sample.wxapi;

import android.widget.Toast;

import com.tencent.tac.social.WeChatBaseHandlerActivity;
import com.tencent.tac.social.share.ShareResult;

public class WXEntryActivity extends WeChatBaseHandlerActivity {

    @Override
    protected void onWeChatShareResult(ShareResult shareResult) {
        super.onWeChatShareResult(shareResult);

        Toast.makeText(this, "wechat share result : " + shareResult.getResult(), Toast.LENGTH_LONG).show();
    }
}