package cn.blcu.destroysquare;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.blcu.destroysquaretool.DateTool;

import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity implements View.OnClickListener {

	private static final int HELP = 1;
	private static final int SETTING = 2;
	private static final int START = 0;
	private static final String TAG = "EV_DEBUG";
	ImageButton help;
	ImageButton setting;
	ImageButton start;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case START:
			// 调用spendPoints消费指定金额的积分，这里示例消费100积分
			if (PointsManager.getInstance(this).spendPoints(10)) {
				int myPointBalance = PointsManager.getInstance(this)
						.queryPoints();
				Log.d("test", "已消费10积分，当前余额为:" + myPointBalance);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GameActivity.class);
				startActivity(intent);
				MainActivity.this.overridePendingTransition(R.anim.rotate_in,
						R.anim.rotate_out);// 参数:读取res中的XML文件实现效果
				// 摇摆
			} else {
				Toast.makeText(MainActivity.this, "消费积分失败(积分余额不足)",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case HELP:
			Intent intent_2 = new Intent();
			intent_2.setClass(MainActivity.this, HelpActivity.class);
			startActivity(intent_2);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);
			;// 参数:读取res中的XML文件实现效果
			break;
		case SETTING:
			Intent intent_3 = new Intent();
			intent_3.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent_3);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);// 参数:读取res中的XML文件实现效果
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化应用的发布ID和密钥，以及设置测试模式
		AdManager.getInstance(this).init("fe6bc463b69db90c",
				"6d8d1e9b776c114b", false);
		// 请务必调用以下代码，告诉SDK应用启动，可以让SDK进行一些初始化操作。
		OffersManager.getInstance(this).onAppLaunch();
		// 调用showOffersWall显示全屏的积分墙界面
		start = (ImageButton) findViewById(R.id.start);
		start.setTag(START);
		help = (ImageButton) findViewById(R.id.help);
		help.setTag(HELP);
		setting = (ImageButton) findViewById(R.id.setting);
		setting.setTag(SETTING);
		start.setOnClickListener(this);
		help.setOnClickListener(this);
		setting.setOnClickListener(this);

		iniADs();
		sharedPreferences = this.getSharedPreferences("Date",
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		if (sharedPreferences.getString("isFirst", "first").equals("first")) {
			// 调用awardPoints可以奖励指定金额的积分，这里示例奖励50积分
			PointsManager.getInstance(this).awardPoints(50);
			int myPointBalance = PointsManager.getInstance(this)
					.queryPoints();
			Toast.makeText(this, "奖励50积分，当前余额:" + myPointBalance,
					Toast.LENGTH_SHORT).show();
			editor.putString("isFirst", "not");
			editor.commit();
		}
	}

	private void iniADs() {
		//实例化广告条
		AdView adView = new AdView(this, AdSize.SIZE_320x50);
		//获取要广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		//将广告条加入到布局中
		adLayout.addView(adView);
	}

	@Override
	protected void onStart() {
		EasyTracker.getInstance().activityStart(this);
		super.onStart();
	}

	@Override
	protected void onStop() {
		EasyTracker.getInstance().activityStop(this);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 请务必在应用退出的时候调用以下代码，告诉SDK应用已经关闭，可以让SDK进行一些资源的释放和清理。
		OffersManager.getInstance(this).onAppExit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent_3 = new Intent();
			intent_3.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent_3);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);// 参数:读取res中的XML文件实现效果
			break;
		case R.id.action_eachday:

			if (!DateTool.getTodayDate().equals(
					sharedPreferences.getString("date",
							DateTool.getDateTodayMinusDay(1)))) {
				// 调用awardPoints可以奖励指定金额的积分，这里示例奖励50积分
				PointsManager.getInstance(this).awardPoints(50);
				int myPointBalance = PointsManager.getInstance(this)
						.queryPoints();
				Toast.makeText(this, "奖励50积分，当前余额:" + myPointBalance,
						Toast.LENGTH_SHORT).show();
				Log.d("EV_DEBUG", "已成功奖励50积分，当前余额为:" + myPointBalance);
				editor.putString("date", DateTool.getTodayDate());
				editor.commit();
			} else {
				Toast.makeText(this, "今天已经签过到,明天再来吧!", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.action_jifen:
			// 调用showOffersWall显示全屏的积分墙界面
			OffersManager.getInstance(this).showOffersWall();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
