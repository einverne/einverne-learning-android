package cn.blcu.destroysquare;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsActivity extends Activity {
	boolean bgflag = true;
	SharedPreferences.Editor editor;
	SharedPreferences sharedPreferences;

	boolean yxflag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		CheckBox bg = (CheckBox) this.findViewById(R.id.CheckBox_bg);
		CheckBox yx = (CheckBox) this.findViewById(R.id.checkBox_yx);
		sharedPreferences = this.getSharedPreferences("flag",
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		bg.setChecked(sharedPreferences.getBoolean("bgflag", true));
		yx.setChecked(sharedPreferences.getBoolean("yxflag", true));

		bg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				editor.putBoolean("bgflag", isChecked);
				editor.commit();
			}
		});

		yx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				editor.putBoolean("yxflag", isChecked);
				editor.commit();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == event.KEYCODE_BACK){
			
			SettingsActivity.this.overridePendingTransition(R.anim.in_from_right,
					R.anim.in_from_left);
		}
		return super.onKeyDown(keyCode, event);
		
		
	}
	

}
