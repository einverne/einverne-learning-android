package cn.blcu.destroysquare;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class GameActivity extends Activity {
	SharedPreferences.Editor editor;
	SquareView myView;
	// private SoundPlayer playMusic;
	Button refresh;
	int refreshChance = 2;
	SharedPreferences sharedPreferences;

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

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.game, menu);
//		return true;
//	}

	protected void setRefreshChance() {
		refresh.setText(refreshChance + "置换");
		if (refreshChance <= 0) {
			refresh.setClickable(false);
			refresh.setEnabled(false);
		}
	}

}
