package cn.blcu.destroysquare;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundPlayer implements MediaPlayer.OnCompletionListener,
		MediaPlayer.OnErrorListener {

	private MediaPlayer bgPlayer;
	private Context context;

	public SoundPlayer(Context context) {
		this.context = context;
	}

	// �����ֲ������ʱ
	@Override
	public void onCompletion(MediaPlayer mp) {
		stopBgSound();
	}

	// �����ֲ��ŷ�������ʱ
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		stopBgSound();
		return false;
	}

	// ���ű������֣������paramIntΪres/raw/Ŀ¼�µ���Ƶ�ļ�������
	public void playBgSound(int paramInt) {
		stopBgSound();

		try {
			// ������Ƶ�ļ�����һ��MeidaPlayer

			bgPlayer = MediaPlayer.create(context, paramInt);
			bgPlayer.setOnCompletionListener(this);
			// �����Ƿ�ѭ������
			bgPlayer.setLooping(true);
			// ��ʼ����
			bgPlayer.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	// ֹͣ���ű�������
	public void stopBgSound() {
		if (bgPlayer == null)
			return;
		if (bgPlayer.isPlaying())
			bgPlayer.stop();
		bgPlayer.release();
		bgPlayer = null;
	}

	public void pauseBgSound() {
		bgPlayer.pause();
	}

	public void startAgainBgSound() {
		bgPlayer.start();
	}
}