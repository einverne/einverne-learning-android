package cn.blcu.destroysquare;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;

public class GameActivity extends Activity {
	private SoundPlayer playMusic;
	private SoundPool sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		playMusic = new SoundPlayer(getApplicationContext());
		playMusic.playBgSound(R.raw.chuyin);
		//initSoundPool();
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

	
	public void initSoundPool()
    {
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.load(this, R.raw.yinx, 1);
        Log.d("mmmmmusic", "init");
       // map.put(2, sp.load(this, R.raw.two, 1));
    }
 
	
	public void playSound(int sound, int number)
    {
    	Log.d("mmmmmusic", "playsound");
        AudioManager am = (AudioManager)this.getSystemService(this.AUDIO_SERVICE);
        //返回当前AlarmManager最大音量
        float audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //返回当前AudioManager对象的音量值
        float audioCurrentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolume/audioMaxVolume;
        sp.play(
        		R.raw.yinx,
                volumnRatio, //左声道音量
                volumnRatio, //右声道音量
                1, //优先级，0为最低
                number, //循环次数，0无不循环，-1无永远循环
                1);//回放速度，值在0.5-2.0之间，1为正常速度
    }
}
