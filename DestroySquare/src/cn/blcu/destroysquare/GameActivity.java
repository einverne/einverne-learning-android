package cn.blcu.destroysquare;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

public class GameActivity extends Activity {
	private static final String TAG = "EV_DEBUG";
	SharedPreferences.Editor editor;
	SquareView myView;
	// private SoundPlayer playMusic;
	Button refresh;
	int refreshChance = 2;
	SharedPreferences sharedPreferences;
	private Tracker mGaTracker;
	private GoogleAnalytics mGaInstance;
	private long start;
	private long end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		myView = new SquareView(GameActivity.this);
		setContentView(myView);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title_bar);
		sharedPreferences = this.getSharedPreferences("flag",
				Context.MODE_PRIVATE);
		// if (sharedPreferences.getBoolean("bgflag", true)) {
		// playMusic = new SoundPlayer(this);
		// playMusic.playBgSound(R.raw.chuyin);
		// }
		// Get the GoogleAnalytics singleton. Note that the SDK uses
		// the application context to avoid leaking the current context.
		mGaInstance = GoogleAnalytics.getInstance(this);

		// Use the GoogleAnalytics singleton to get a Tracker.
		mGaTracker = mGaInstance.getTracker("UA-40648258-1"); // Placeholder
																// tracking ID.

		findViewById(R.id.button_pause).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						myView.pause();
						// playMusic.stopBgSound();
					}
				});
		refresh = (Button) findViewById(R.id.button_refresh);
		refresh.setText(refreshChance + "置换");
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myView.refresh();
				refreshChance--;
				setRefreshChance();
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/**
		 * 增加中间过渡特效
		 */
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			if (myView.playMusic != null) {
				myView.playMusic.pauseBgSound();
			}
			myView.pause();
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// if(sharedPreferences.getBoolean("bgflag", true)){
			// playMusic.stopBgSound();
			// }
			if (myView.playMusic != null) {
				myView.playMusic.stopBgSound();
			}
			myView.timer.cancel();
			myView.timertask.cancel();
			finish();
			this.overridePendingTransition(R.anim.in_from_left,
					R.anim.out_to_right);// 参数:读取res中的XML文件实现效果
		}
		return super.onKeyDown(keyCode, event);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.game, menu);
	// return true;
	// }

	protected void setRefreshChance() {
		refresh.setText(refreshChance + "置换");
		if (refreshChance <= 0) {
			refresh.setClickable(false);
			refresh.setEnabled(false);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this); // Add this method.
		// Send a screen view when the Activity is displayed to the user.
		mGaTracker.sendView("/GameActivity");
		start = System.currentTimeMillis();
		Log.d(TAG, "start:"+start);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this); // Add this method.
		end = System.currentTimeMillis();
		Log.d(TAG, "end:"+end);
		long gameTime = end - start;
		mGaTracker.sendTiming("ui_time", gameTime, "game_time", "gameview");
	}

}
