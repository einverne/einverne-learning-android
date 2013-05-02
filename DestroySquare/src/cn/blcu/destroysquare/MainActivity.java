package cn.blcu.destroysquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity implements View.OnClickListener {

	private static final int START = 0;
	private static final int HELP = 1;
	private static final int SETTING = 2;
	ImageButton start;
	ImageButton help;
	ImageButton setting;

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
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case START:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, GameActivity.class);
			startActivity(intent);
			MainActivity.this.overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);// 参数:读取res中的XML文件实现效果
			break;
		case HELP:
			Intent intent_2 = new Intent();
			intent_2.setClass(MainActivity.this, HelpActivity.class);
			startActivity(intent_2);
			MainActivity.this.overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);// 参数:读取res中的XML文件实现效果
			break;
		case SETTING:
			Intent intent_3 = new Intent();
			intent_3.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent_3);
			MainActivity.this.overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);// 参数:读取res中的XML文件实现效果
			break;
		default:
			break;
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

}
