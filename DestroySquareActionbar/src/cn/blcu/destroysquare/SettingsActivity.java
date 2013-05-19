package cn.blcu.destroysquare;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdManager.ErrorCode;
import cn.domob.android.ads.DomobAdView;

import com.google.analytics.tracking.android.EasyTracker;

public class SettingsActivity extends Activity {
	public static final String PUBLISHER_ID = "56OJzw2IuNXifZqbis";
	public static final String InlinePPID = "16TLm5ToApCv4NUHYH4Vk63k";
	
	boolean bgflag = true;
	SharedPreferences.Editor editor;
	SharedPreferences sharedPreferences;

	boolean yxflag = true;
	RelativeLayout ad;
	DomobAdView domobAdView;	//300*25

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		CheckBox bg = (CheckBox) this.findViewById(R.id.CheckBox_bg);
		CheckBox yx = (CheckBox) this.findViewById(R.id.CheckBox_yx);
		CheckBox xz = (CheckBox) this.findViewById(R.id.checkBox_xz);
		ad = (RelativeLayout)findViewById(R.id.ad);
		domobAdView = new DomobAdView(this, PUBLISHER_ID, InlinePPID, DomobAdView.INLINE_SIZE_320X50);
		
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
		
		domobAdView.setAdEventListener(new DomobAdEventListener() {
			
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
				return SettingsActivity.this;
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
		
		ad.addView(domobAdView);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.help, menu);
//		return true;
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		finish();
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
			SettingsActivity.this.overridePendingTransition(R.anim.in_from_left,
					R.anim.out_to_right);
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
