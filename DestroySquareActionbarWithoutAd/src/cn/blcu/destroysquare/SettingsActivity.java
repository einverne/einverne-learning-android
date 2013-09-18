package cn.blcu.destroysquare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;

public class SettingsActivity extends SherlockActivity {
	public static final String PUBLISHER_ID = "56OJzw2IuNXifZqbis";
	public static final String InlinePPID = "16TLm5ToApCv4NUHYH4Vk63k";

	boolean bgflag = true;
	SharedPreferences.Editor editor;
	SharedPreferences sharedPreferences;

	boolean yxflag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		CheckBox bg = (CheckBox) this.findViewById(R.id.CheckBox_bg);
		CheckBox yx = (CheckBox) this.findViewById(R.id.CheckBox_yx);
		CheckBox xz = (CheckBox) this.findViewById(R.id.checkBox_xz);

		sharedPreferences = this.getSharedPreferences("flag",
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		bg.setChecked(sharedPreferences.getBoolean("bgflag", true));
		yx.setChecked(sharedPreferences.getBoolean("yxflag", true));
		xz.setChecked(sharedPreferences.getBoolean("xzflag", false));

		bg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				editor.putBoolean("bgflag", isChecked);
				editor.commit();
			}
		});

		yx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				editor.putBoolean("yxflag", isChecked);
				editor.commit();
			}
		});

		xz.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				editor.putBoolean("xzflag", isChecked);
				editor.commit();
			}
		});

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.help, menu);
	// return true;
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		finish();
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			SettingsActivity.this.overridePendingTransition(
					R.anim.in_from_left, R.anim.out_to_right);
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		// The rest of your onStart() code.
		EasyTracker.getInstance().activityStart(this); // Add this method.
		super.onStart();
	}

	@Override
	protected void onStop() {
		// The rest of your onStop() code.
		EasyTracker.getInstance().activityStop(this); // Add this method.
		super.onStop();
	}

}
