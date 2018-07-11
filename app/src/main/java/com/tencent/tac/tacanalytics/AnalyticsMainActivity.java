package com.tencent.tac.tacanalytics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tencent.tac.R;
import com.tencent.tac.analytics.TACAnalyticsEvent;
import com.tencent.tac.analytics.TACAnalyticsService;

import java.util.Properties;

public class AnalyticsMainActivity extends AppCompatActivity {

	private static final String TAG = "TACAnalyticsDemo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analytics_activity_first);
	}

	private void showFeedback(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void trackEvent(View v) {
		String eventId = "oneEvent";
		TACAnalyticsService.getInstance().trackEvent(this, new TACAnalyticsEvent(eventId));
		showFeedback(String.format("已统计事件 %s", eventId));
	}

	public void trackEventWithArgs(View v) {
		String eventId = "oneEvent";
		String paramKey = "twoKey";
		String paramValue = "twoValue";
		Properties properties = new Properties();
		properties.put(paramKey, paramValue);
		TACAnalyticsService.getInstance().trackEvent(this, new TACAnalyticsEvent(eventId, properties));
		showFeedback(String.format("已统计事件 %s , 参数 {%s = %s}", eventId, paramKey, paramValue));
	}

	public void trackDurationEvent(View v) {
		String eventId = "oneDurationEvent";
		int duration = 2000;
		TACAnalyticsService.getInstance().trackEventDuration(this, new TACAnalyticsEvent(eventId), duration);
		showFeedback(String.format("已统计事件 %s , 时长 %s", eventId, duration + "ms"));
	}

	public void trackDurationEventBegin(View v) {
		String eventId = "twoDurationEvent";
		String paramKey = "twoKeyDuration";
		String paramValue = "twoValueDuration";
		Properties properties = new Properties();
		properties.put(paramKey, paramValue);
		TACAnalyticsService.getInstance().trackEventDurationBegin(this, new TACAnalyticsEvent(eventId, properties));
		showFeedback(String.format("开始统计时长事件 %s , 参数 {%s = %s}", eventId, paramKey, paramValue));
	}

	public void trackDurationEventEnd(View v) {
		String eventId = "twoDurationEvent";
		String paramKey = "twoKeyDuration";
		String paramValue = "twoValueDuration";
		Properties properties = new Properties();
		properties.put(paramKey, paramValue);
		TACAnalyticsService.getInstance().trackEventDurationEnd(this, new TACAnalyticsEvent(eventId, properties));
		showFeedback(String.format("结束统计时长事件 %s , 参数 {%s = %s}", eventId, paramKey, paramValue));
	}

	public void reportCustomProperty(View view) {
		Properties properties = new Properties();
		properties.setProperty("financing_degree", "10");
		TACAnalyticsService.getInstance().setUserProperties(this, properties);
		showFeedback(String.format("上报用户属性  %s", properties));
	}

	public void newSession(View v) {
		TACAnalyticsService.getInstance().exchangeNewSession(this);
		showFeedback("开启一个新回话");
	}

	public void trackPage(View v) {
		startActivity(new Intent(this, SecondActivity.class));
	}

	public void startWebView(View v) {
		startActivity(new Intent(this, WebViewActivity.class));
	}

//	public void trackNetworkMetrics(View v) {
//		// 新建监控接口对象
//		TACNetworkMetrics monitor = new TACNetworkMetrics("ping:www.qq.com");
//		// 接口开始执行
//		String ip = "www.qq.com";
//		Runtime run = Runtime.getRuntime();
//		Process proc = null;
//		try {
//			String str = "ping -c 3 -i 0.2 -W 1 " + ip;
//			long starttime = System.currentTimeMillis();
//			proc = run.exec(str);
//			int retCode = proc.waitFor();
//			long difftime = System.currentTimeMillis() - starttime;
//
//			monitor
//			// 设置接口耗时
//			.setMillisecondsConsume(difftime)
//			// 设置接口返回码
//			.setReturnCode(retCode)
//			// 设置请求包大小，若有的话
//			.setReqSize(1000)
//			// 设置响应包大小，若有的话
//			.setRespSize(2000);
//			// 设置抽样率，默认为1，表示100%。如果是50%，则填2(100/50)，如果是25%，则填4(100/25)，以此类推。
//			// .setSampling(2)
//
//			if (retCode == 0) {
//				Log.d(TAG, "ping连接成功");
//				// 标记为成功
//				monitor.setResultType(TACNetworkMetrics.SUCCESS_RESULT_TYPE);
//			} else {
//				Log.d(TAG, "ping测试失败");
//				// 标记为逻辑失败，可能由网络未连接等原因引起的，但对于业务来说不是致命的，是可容忍的
//				monitor.setResultType(TACNetworkMetrics.LOGIC_FAILURE_RESULT_TYPE);
//			}
//		} catch (Exception e) {
//			Log.d(TAG, e.toString());
//			// 接口调用出现异常，致命的，标识为失败
//			monitor.setResultType(TACNetworkMetrics.FAILURE_RESULT_TYPE);
//		} finally {
//			proc.destroy();
//		}
//		// 上报接口监控的信息
//		TACAnalyticsService.getInstance().trackNetworkMetrics(this, monitor);
//	}
//
//	public void getProperty(View v) {
//		Log.d(TAG, "getProperty() = " + TACAnalyticsService.getInstance().getCustomProperty(this, "sex"));
//	}
}
