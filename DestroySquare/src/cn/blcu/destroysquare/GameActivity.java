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
		 * �����м������Ч
		 */
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			this.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);//����:��ȡres�е�XML�ļ�ʵ��Ч��
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
        //���ص�ǰAlarmManager�������
        float audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //���ص�ǰAudioManager���������ֵ
        float audioCurrentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolume/audioMaxVolume;
        sp.play(
        		R.raw.yinx,
                volumnRatio, //����������
                volumnRatio, //����������
                1, //���ȼ���0Ϊ���
                number, //ѭ��������0�޲�ѭ����-1����Զѭ��
                1);//�ط��ٶȣ�ֵ��0.5-2.0֮�䣬1Ϊ�����ٶ�
    }
}
