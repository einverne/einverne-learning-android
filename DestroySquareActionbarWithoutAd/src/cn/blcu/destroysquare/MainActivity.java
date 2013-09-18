package cn.blcu.destroysquare;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.blcu.destroysquaretool.DateTool;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends SherlockActivity implements
		View.OnClickListener {

	private static final int HELP = 1;
	private static final int SETTING = 2;
	private static final int START = 0;
	private static final String TAG = "EV_DEBUG";

	ImageButton help;
	ImageButton setting;
	ImageButton start;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	private Tracker myTracker;
	private GoogleAnalytics mGaInstance;

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case START:
			myTracker.sendEvent("ui_action", "button_press", "startGame", null);

			Intent intent = new Intent();
			intent.setClass(MainActivity.this, GameActivity.class);
			startActivity(intent);
			this.overridePendingTransition(R.anim.new_dync_in_from_right,
					R.anim.new_dync_out_to_left);
			break;
		case HELP:
			myTracker.sendEvent("ui_action", "button_press", "helpActivity",
					null);
			Intent intent_2 = new Intent();
			intent_2.setClass(MainActivity.this, HelpActivity.class);
			startActivity(intent_2);
			MainActivity.this.overridePendingTransition(
					R.anim.new_dync_in_from_right, R.anim.new_dync_out_to_left);
			// 参数:读取res中的XML文件实现效果
			break;
		case SETTING:
			myTracker.sendEvent("ui_action", "button_press", "settingActivity",
					null);
			Intent intent_3 = new Intent();
			intent_3.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent_3);
			MainActivity.this.overridePendingTransition(
					R.anim.new_dync_in_from_right, R.anim.new_dync_out_to_left);// 参数:读取res中的XML文件实现效果
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

		// Get the GoogleAnalytics singleton. Note that the SDK uses
		// the application context to avoid leaking the current context.
		mGaInstance = GoogleAnalytics.getInstance(this);

		// Use the GoogleAnalytics singleton to get a Tracker.
		myTracker = mGaInstance.getTracker("UA-40648258-1"); // Placeholder
																// tracking ID.

		start = (ImageButton) findViewById(R.id.start);
		start.setTag(START);
		help = (ImageButton) findViewById(R.id.help);
		help.setTag(HELP);
		setting = (ImageButton) findViewById(R.id.setting);
		setting.setTag(SETTING);
		start.setOnClickListener(this);
		help.setOnClickListener(this);
		setting.setOnClickListener(this);

		LinearLayout linear_start = (LinearLayout) findViewById(R.id.linear_play);
		linear_start.setTag(START);
		linear_start.setOnClickListener(this);

		LinearLayout linear_help = (LinearLayout) findViewById(R.id.linear_how);
		linear_help.setTag(HELP);
		linear_help.setOnClickListener(this);
		LinearLayout linear_setting = (LinearLayout) findViewById(R.id.linear_setting);
		linear_setting.setTag(SETTING);
		linear_setting.setOnClickListener(this);

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
		super.onDestroy();
		// 请务必在应用退出的时候调用以下代码，告诉SDK应用已经关闭，可以让SDK进行一些资源的释放和清理。
		OffersManager.getInstance(this).onAppExit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			myTracker.sendEvent("ui_action", "menu_press", "settingActivity",
					null);
			Intent intent_3 = new Intent();
			intent_3.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent_3);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);// 参数:读取res中的XML文件实现效果
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
