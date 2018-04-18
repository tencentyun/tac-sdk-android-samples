package com.tencent.tac;

import android.app.Application;
import android.content.Context;

import com.tencent.tac.analytics.TACAnalyticsOptions;
import com.tencent.tac.analytics.TACAnalyticsStrategy;
import com.tencent.tac.option.TACApplicationOptions;

public class App extends Application {

	@Override

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);

		// 高级设置
		TACApplicationOptions applicationOptions = TACApplicationOptions.newDefaultOptions(this);

		// 设置行为统计数据上报的策略
		TACAnalyticsOptions analyticsOptions = applicationOptions.sub("analytics");
		analyticsOptions.strategy(TACAnalyticsStrategy.INSTANT); // 立即发送

		// 使用自定义设置
		TACApplication.configureWithOptions(this, applicationOptions);
	}
}




