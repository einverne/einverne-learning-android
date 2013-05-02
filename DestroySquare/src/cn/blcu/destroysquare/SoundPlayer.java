package cn.blcu.destroysquare;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {;
	
	private MediaPlayer bgPlayer;
	private Context context;
	
	public SoundPlayer(Context context) {
		this.context = context;
	}
        //�����ֲ������ʱ
	public void onCompletion(MediaPlayer mp) {
		stopBgSound();
	}
        //�����ֲ��ŷ�������ʱ
	public boolean onError(MediaPlayer mp, int what, int extra) {
		stopBgSound();
		return false;
	}
	//���ű������֣������paramIntΪres/raw/Ŀ¼�µ���Ƶ�ļ�������
	public void playBgSound(int paramInt) {
		stopBgSound();
		
		try {
                        //������Ƶ�ļ�����һ��MeidaPlayer
			MediaPlayer mediaPlayer = MediaPlayer.create(context, paramInt);
			bgPlayer = mediaPlayer;
			bgPlayer.setOnCompletionListener(this);
                        //�����Ƿ�ѭ������
			bgPlayer.setLooping(true);
                        //��ʼ����
			bgPlayer.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} 
	}
	//ֹͣ���ű�������
	public void stopBgSound() {
		if(bgPlayer == null) 
			return;
		if(bgPlayer.isPlaying())
			bgPlayer.stop();
		bgPlayer.release();
		bgPlayer = null;
	}
}