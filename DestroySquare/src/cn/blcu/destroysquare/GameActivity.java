package cn.blcu.destroysquare;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;

public class GameActivity extends Activity {
	private SoundPlayer playMusic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		playMusic = new SoundPlayer(getApplicationContext());
		playMusic.playBgSound(R.raw.chuyin);
		
		SquareView myView=new SquareView(GameActivity.this);
		setContentView(myView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/**
		 * 增加中间过渡特效
		 */
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			this.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);//参数:读取res中的XML文件实现效果
			playMusic.stopBgSound();
		}
		return super.onKeyDown(keyCode, event);
	}

}
