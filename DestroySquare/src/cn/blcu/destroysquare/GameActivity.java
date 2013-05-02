package cn.blcu.destroysquare;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class GameActivity extends Activity {
	private SoundPlayer playMusic;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		playMusic = new SoundPlayer(getApplicationContext());
		playMusic.playBgSound(R.raw.chuyin);
		
		SquareView myView=new SquareView(GameActivity.this);
		setContentView(myView);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.game, menu);
//		return true;
//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/**
		 * 增加中间过渡特效
		 */
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(sharedPreferences.getBoolean("bgflag", true)){
			playMusic.stopBgSound();
			}
			finish();
			this.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);//参数:读取res中的XML文件实现效果
		}
		return super.onKeyDown(keyCode, event);
	}

	

}
