package cn.blcu.destroysquare;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.blcu.destroysquaretool.DateTool;
import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdView;
import cn.domob.android.ads.DomobAdManager.ErrorCode;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends SherlockActivity implements View.OnClickListener {

	private static final int HELP = 1;
	private static final int SETTING = 2;
	private static final int START = 0;
	private static final String TAG = "EV_DEBUG";
	public static final String InterstitialPPID = "16TLm5ToApCv4NUH4b_Xhj1z";
	public static final String PUBLISHER_ID = "56OJzw2IuNXifZqbis";
	public static final String InlinePPID = "16TLm5ToApCv4NUHYH4Vk63k";
	protected static final Object PASSCODE = "einverne";

	ImageButton help;
	ImageButton setting;
	ImageButton start;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	private Tracker myTracker;
	private GoogleAnalytics mGaInstance;
	
	RelativeLayout mAdContainer;
	DomobAdView mAdview320x50;

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case START:
			myTracker.sendEvent("ui_action", "button_press", "startGame", null);
			
			sharedPreferences = getSharedPreferences("PASSCODE", MODE_PRIVATE);
			if (sharedPreferences.getBoolean("Forever", false)) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GameActivity.class);
				startActivity(intent);
				this.overridePendingTransition(R.anim.new_dync_in_from_right,
						R.anim.new_dync_out_to_left);
				break;
			}
			// 调用spendPoints消费指定金额的积分，这里示例消费100积分
			if (PointsManager.getInstance(this).spendPoints(10)) {
				int myPointBalance = PointsManager.getInstance(this)
						.queryPoints();
				Log.d("EV_DEBUG", "已消费10积分，当前余额为:" + myPointBalance);
				Toast.makeText(getApplicationContext(), "已消费10积分，当前余额为:" + myPointBalance, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GameActivity.class);
				startActivity(intent);
//				this.overridePendingTransition(R.anim.rotate_in,
//						R.anim.rotate_out);// 参数:读取res中的XML文件实现效果
				this.overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);// 参数:读取res中的XML文件实现效果
				// 摇摆
			} else {
				Toast.makeText(MainActivity.this, "消费积分失败(积分余额不足),请去获取积分,跳转到积分墙",
						Toast.LENGTH_SHORT).show();
				myTracker.sendEvent("ui_action", "no_jifen", "getJifen", null);
				// 调用showOffersWall显示全屏的积分墙界面
				OffersManager.getInstance(this).showOffersWall();
			}

			break;
		case HELP:
			myTracker.sendEvent("ui_action", "button_press", "helpActivity",
					null);
			Intent intent_2 = new Intent();
			intent_2.setClass(MainActivity.this, HelpActivity.class);
			startActivity(intent_2);
			MainActivity.this.overridePendingTransition(R.anim.new_dync_in_from_right,
					R.anim.new_dync_out_to_left);
			;// 参数:读取res中的XML文件实现效果
			break;
		case SETTING:
			myTracker.sendEvent("ui_action", "button_press", "settingActivity",
					null);
			Intent intent_3 = new Intent();
			intent_3.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent_3);
			MainActivity.this.overridePendingTransition(R.anim.new_dync_in_from_right,
					R.anim.new_dync_out_to_left);// 参数:读取res中的XML文件实现效果
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
		
		LinearLayout linear_start = (LinearLayout)findViewById(R.id.linear_play);
		linear_start.setTag(START);
		linear_start.setOnClickListener(this);
		
		LinearLayout linear_help = (LinearLayout)findViewById(R.id.linear_how);
		linear_help.setTag(HELP);
		linear_help.setOnClickListener(this);
		LinearLayout linear_setting = (LinearLayout)findViewById(R.id.linear_setting);
		linear_setting.setTag(SETTING);
		linear_setting.setOnClickListener(this);
		

		iniADs();
		iniDomobInlineAD();
		sharedPreferences = this.getSharedPreferences("Date",
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		if (sharedPreferences.getString("isFirst", "first").equals("first")) {
			// 调用awardPoints可以奖励指定金额的积分，这里示例奖励50积分
			PointsManager.getInstance(this).awardPoints(50);
			int myPointBalance = PointsManager.getInstance(this).queryPoints();
			Toast.makeText(this, "奖励50积分，当前余额:" + myPointBalance,
					Toast.LENGTH_SHORT).show();
			editor.putString("isFirst", "not");
			editor.commit();
		}
		
		
		mAdview320x50.setAdEventListener(new DomobAdEventListener() {
			
			@Override
			public void onDomobLeaveApplication(DomobAdView arg0) {
				
			}
			
			@Override
			public void onDomobAdReturned(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Context onDomobAdRequiresCurrentContext() {
				// TODO Auto-generated method stub
				return MainActivity.this;
			}
			
			@Override
			public void onDomobAdOverlayPresented(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDomobAdOverlayDismissed(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDomobAdFailed(DomobAdView arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDomobAdClicked(DomobAdView arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mAdContainer.addView(mAdview320x50);
	}

	private void iniDomobInlineAD() {
		mAdContainer = (RelativeLayout)findViewById(R.id.domobAdcontainer);
		mAdview320x50 = new DomobAdView(this, PUBLISHER_ID, InlinePPID, DomobAdView.INLINE_SIZE_320X50);
	}

	private void iniADs() {
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.SIZE_320x50);
		// 获取要广告条的布局
		LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
		// 将广告条加入到布局中
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
		case R.id.action_eachday:
			myTracker.sendEvent("ui_action", "menu_press", "eachday", null);
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
				int myPointBalance = PointsManager.getInstance(this)
						.queryPoints();
				Toast.makeText(this, "今天已经签过到,明天再来吧!当前余额:"+myPointBalance, Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.action_jifen:
			myTracker.sendEvent("ui_action", "menu_press", "getJifen", null);
			// 调用showOffersWall显示全屏的积分墙界面
			OffersManager.getInstance(this).showOffersWall();
			break;
		case R.id.update:
			UmengUpdateAgent.update(getApplication());
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
				
				@Override
				public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
					switch (updateStatus) {
		            case 0: // has update
		                UmengUpdateAgent.showUpdateDialog(MainActivity.this, updateInfo);
		                break;
		            case 1: // has no update
		                Toast.makeText(MainActivity.this, "没有更新", Toast.LENGTH_SHORT)
		                        .show();
		                break;
		            case 2: // none wifi
		                Toast.makeText(MainActivity.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT)
		                        .show();
		                break;
		            case 3: // time out
		                Toast.makeText(MainActivity.this, "超时", Toast.LENGTH_SHORT)
		                        .show();
		                break;
		            }
				}
			});
			UmengUpdateAgent.setOnDownloadListener(new UmengDownloadListener() {
				
				@Override
				public void OnDownloadEnd(int result) {
			        if (result == 1) {
			        	Toast.makeText(MainActivity.this, "下载成功" , Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(MainActivity.this, "下载失败" , Toast.LENGTH_SHORT).show();
					}
				}
			});
			break;
		case R.id.passcode:
			LayoutInflater inflater = LayoutInflater.from(getApplication());
			final View dialogview = inflater.inflate(R.layout.dialogview, null);
			new AlertDialog.Builder(MainActivity.this).setTitle("请输入passcode:")
			.setView(dialogview)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					EditText et_passcode = (EditText)dialogview.findViewById(R.id.et_passcode);	
					Editable editable = et_passcode.getText();
					if (editable.toString().matches("")) {
						Toast.makeText(getApplication(), "请输入", Toast.LENGTH_SHORT).show();
					}else{
						String passcode = editable.toString();
						if (passcode.equals(PASSCODE)) {
							sharedPreferences = getSharedPreferences("PASSCODE", MODE_PRIVATE);
							editor = sharedPreferences.edit();
							editor.putBoolean("Forever", true);
							editor.commit();
						}
					}
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).create().show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
