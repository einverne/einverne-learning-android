package cn.blcu.destroysquare;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener {

	private static final int HELP = 1;
	private static final int SETTING = 2;
	private static final int START = 0;
	ImageButton help;
	ImageButton setting;
	ImageButton start;

	

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case START:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, GameActivity.class);
			startActivity(intent);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);// 参数:读取res中的XML文件实现效果
			//摇摆  
			
			break;
		case HELP:
			Intent intent_2 = new Intent();
			intent_2.setClass(MainActivity.this, HelpActivity.class);
			startActivity(intent_2);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);;// 参数:读取res中的XML文件实现效果
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

		start = (ImageButton) findViewById(R.id.start);
		start.setTag(START);
		help = (ImageButton) findViewById(R.id.help);
		help.setTag(HELP);
		setting = (ImageButton) findViewById(R.id.setting);
		setting.setTag(SETTING);
		start.setOnClickListener(this);
		help.setOnClickListener(this);
		setting.setOnClickListener(this);
		
	
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

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

}
