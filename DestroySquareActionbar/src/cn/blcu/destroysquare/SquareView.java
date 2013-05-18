package cn.blcu.destroysquare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.R.color;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SquareView extends View {

	private static final int column = 7;

	public static int level = 3; // level to setting how many kind of
	private static final int line = 9;
	public static final int MSG_GAME_LEVEL_THREE = 3;
	public static final int MSG_GAME_LEVEL_TWO = 2;

	public static final int MSG_GAME_OVER = 1;
	protected static final int SCORE_THREE_LEVEL = 300;
	protected static final int SCORE_TWO_LEVEL = 100;
	private List<Integer> colors;
	public Context context;
	private int countTime = 100;
	SharedPreferences.Editor editor;
	SharedPreferences.Editor editor_s;
	private int Flag_Time = 0;
	Handler handler;
	private boolean isTimerStop = false;
	int lengthOfSquare;// ����ÿ��С����ı߳�
	boolean level_three = false;
	boolean level_two = false;
	private int maxX = 0;

	private int maxY = 0;
	private int minX = 0;
	private int minY = 0;
	private int score;
	private List<Square> SelectSquares;

	SharedPreferences sharedPreferences;
	SharedPreferences sharedPreferences_s;
	private int sound;
	public boolean soundFlag = true;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();

	private List<Square> Squares;
	private int state = 0;
	private String TAG = "EV_DEBUG";
	Timer timer;
	TimerTask timertask;
	public SoundPlayer playMusic;
	
	
	public SquareView(Context context) {
		super(context);
		 /**���ÿ��ƽ��� **/
		 setFocusable(true);
		iniSquareView(context);
	}

	public SquareView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 setFocusable(true);
		iniSquareView(context);
		
	}

	public SquareView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFocusable(true);
		iniSquareView(context);
		 
	}

	/**
	 * SelectSquares�������,����ȡ�����������б�ѡ�е�������״̬
	 */
	private void clearSelectSquares() {
		for (int j = 0; j < SelectSquares.size(); j++) {
			SelectSquares.get(j).setSelected(false);
		}
		SelectSquares.clear();
	}

	protected void gameover() {
		if (playMusic != null) {
			playMusic.stopBgSound();
		}
		int high_score = sharedPreferences.getInt("High_Score", 0);
		Log.d(TAG, "High_Score:" + high_score + " score:" + score);
		if (score > high_score) {
			editor.putInt("High_Score", score);
			editor.commit();
		}
		new AlertDialog.Builder(this.context)
				.setTitle("Game Over")
				.setIcon(R.drawable.ic_launcher)
				.setMessage(
						"��߷�:" + sharedPreferences.getInt("High_Score", 0)
								+ "\n" + "���ε÷�:" + score)
				.setPositiveButton("Try again",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								GameActivity GA = (GameActivity) context;
								GA.finish();
								Intent intent = new Intent(GA,
										GameActivity.class);
								GA.startActivity(intent);
							}
						})
				.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
								GameActivity GA = (GameActivity) context;
								GA.finish();
							}
						}).setCancelable(false).show();
	}

	private void getBoundrayValue() {

		int x[] = { 0, 0 };
		int y[] = { 0, 0 };
		int q1 = 0;
		int q2 = 0;

		for (int i = 0; i < 3; i++) {
			int x1 = SelectSquares.get(i).GetX();
			int y1 = SelectSquares.get(i).GetY();
			for (int j = i + 1; j < 4; j++) {
				int x2 = SelectSquares.get(j).GetX();
				int y2 = SelectSquares.get(j).GetY();
				if (x1 == x2) {
					x[q1++] = x1;
				}
				if (y1 == y2) {
					y[q2++] = y1;
				}
			}
		}
		if (x[0] > x[1]) {
			minX = x[1];
			maxX = x[0];
		} else {
			minX = x[0];
			maxX = x[1];
		}
		if (y[0] > y[1]) {
			minY = y[1];
			maxY = y[0];
		} else {
			minY = y[0];
			maxY = y[1];
		}
	}

	private Square getWhichSelected(float x, float y) {
		Square square = null;
		for (int i = 0; i < Squares.size(); i++) {
			int left = Squares.get(i).GetX() - lengthOfSquare / 2;
			int top = Squares.get(i).GetY() - lengthOfSquare / 2;
			int right = left + lengthOfSquare;
			int bottom = top + lengthOfSquare;
			if (x > left && x < right && y > top && y < bottom) {
				square = Squares.get(i);
			}
		}
		return square;
	}

	/**
	 * ����ѡ�е���������ɫ�Ƿ�һ���ĺ���
	 * 
	 * @param selectedSquare
	 * @return
	 */
	private boolean identifyColor(Square selectedSquare) {
		Square existedSquare = SelectSquares.get(0);
		int color1 = selectedSquare.GetColor();
		int color2 = existedSquare.GetColor();
		boolean b = isSameColor(color1, color2);
		return b;
	}

	/**
	 * ��ʼ��SquareView
	 * 
	 * @param context
	 */
	private void iniSquareView(Context context) {
		Log.d(TAG, "iniSquareView");
		this.context = context;
		this.Squares = new ArrayList<Square>();
		this.SelectSquares = new ArrayList<Square>();
		this.colors = new ArrayList<Integer>();
		level = 3;
		score = 0;
	
		initSounds();
		
		sharedPreferences = context.getSharedPreferences("High_Score",
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		sharedPreferences_s = context.getSharedPreferences("flag",
				Context.MODE_PRIVATE);
		initBgSounds();
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_GAME_OVER:
					// GAME OVER;
					gameover();
					Log.d(TAG, "receive MSG Game Over Score:" + score);
					break;
				case MSG_GAME_LEVEL_TWO:
					level_two();
					Log.d(TAG, "level" + level + " counttime:" + countTime);
					break;
				case MSG_GAME_LEVEL_THREE:
					level_three();
					Log.d(TAG, "level" + level + " counttime:" + countTime);
					break;
				default:
					break;
				}
			}
		};
		timertask = new TimerTask() {

			@Override
			public void run() {
				if (isTimerStop == true) {
					return;
				}
				postInvalidate();
				Log.d(TAG, "Score:" + score + " Time:" + countTime);
				if (countTime <= 0) {
					handler.sendEmptyMessage(SquareView.MSG_GAME_OVER);
					timer.cancel();
					Log.d(TAG, "Send MSG_Game_Over");
					return;
				}
				countTime--;
				if (score > SCORE_TWO_LEVEL && !level_two) {
					handler.sendEmptyMessage(SquareView.MSG_GAME_LEVEL_TWO);
					level_two = true;
					Log.d(TAG, "Send MSG Game level Two");
					return;
				}
				if (score > SCORE_THREE_LEVEL && !level_three) {
					handler.sendEmptyMessage(SquareView.MSG_GAME_LEVEL_THREE);
					level_three = true;
					Log.d(TAG, "Send MSG Game level Three");
					return;
				}
			}
		};
		timer = new Timer();
		timer.schedule(timertask, 0, 1000);

	}

	private void InitColors() {
		if (sharedPreferences_s.getBoolean("xzflag", false)) {
			this.colors.add(getResources().getColor(R.color.blue_hc));
			this.colors.add(getResources().getColor(R.color.green_hc));
			this.colors.add(getResources().getColor(R.color.yellow_hc));
			this.colors.add(getResources().getColor(R.color.purple_hc));
			this.colors.add(getResources().getColor(R.color.red_hc));
		}
		else{
			this.colors.add(getResources().getColor(R.color.blue_normal));
			this.colors.add(getResources().getColor(R.color.green_normal));
			this.colors.add(getResources().getColor(R.color.yellow_normal));
			this.colors.add(getResources().getColor(R.color.purple_normal));
			this.colors.add(getResources().getColor(R.color.red_normal));
		}
	}

	public void initialize() {
		InitColors();
		InitSquareLength();
		InitSquares();
		
	}
	
	public void initBgSounds() {
		//Log.d("hhhhhhhh",sharedPreferences_s.getAll()+"");
		if (sharedPreferences_s.getBoolean("bgflag", true)) {
			playMusic = new SoundPlayer(context);
			playMusic.playBgSound(R.raw.chuyin);
		}

		
	}
	public void initSounds() {
		// ��ʼ��soundPool ����,��һ�������������ж��ٸ�������ͬʱ����,��2����������������,������������������Ʒ��
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		soundMap.put(1 , soundPool.load(context, R.raw.yinx , 1));
		soundMap.put(2 , soundPool.load(context, R.raw.end , 1));

		//sound = soundPool.load(context, R.raw.yinx, 1);

	}

	private void InitSquareLength() {
		Log.d(TAG, "InitSquareLength");
		int width = this.getWidth();
		this.lengthOfSquare = width / column;
		Log.d("EV_DEBUG:lengthOfSquare", "" + lengthOfSquare);
	}

	/**
	 * ��ʼ������list
	 */
	private void InitSquares() {
		int width = lengthOfSquare * column;
		int height = lengthOfSquare * line;
		int screen_width = this.getWidth();
		int screen_height = this.getHeight();
		int offset_x = (screen_width - width) / 2; // ���Ҿ��� , margin
		int offset_y = (screen_height - height - offset_x); // �Ѿ�Ԥ����50�����غ����¾���
		// offset_x,offset_y���ǵ�һ��Square���Ͻǵ�����
		// ps:�������Ͻ���������㣿����������������������
		int x = offset_x + lengthOfSquare / 2;
		int y = offset_y + lengthOfSquare / 2;
		for (int j = 0; j < line; j++) {
			SetEachLine(x, y, lengthOfSquare);
			y += lengthOfSquare;
			x = offset_x + lengthOfSquare / 2;
		}
		Log.d("check", "Check" + String.valueOf(lengthOfSquare));
		Log.d("width", "width" + String.valueOf(width));
		Log.d("height", "height" + String.valueOf(height));
	}

	private boolean isRect_four(Square selectedSquare) {
		int count_x = 0;
		int count_y = 0;
		for (int i = 0; i < 3; i++) {
			int x1 = SelectSquares.get(i).GetX();
			int y1 = SelectSquares.get(i).GetY();
			for (int j = i + 1; j < 4; j++) {
				int x2 = SelectSquares.get(j).GetX();
				int y2 = SelectSquares.get(j).GetY();
				if (x1 == x2) {
					count_x++;
				}
				if (y1 == y2) {
					count_y++;
				}
			}
		}
		if (!(count_x == 2 && count_y == 2)) {
			return false;
		} else {
			return true;
		}
	}

	// �ж��Ƿ�����һ����������
	private boolean isRect_three(Square selectedSquare) {
		int count_x = 0;
		int count_y = 0;
		for (int i = 0; i < 2; i++) {
			int x1 = SelectSquares.get(i).GetX();
			int y1 = SelectSquares.get(i).GetY();
			for (int j = i + 1; j < 3; j++) {
				int x2 = SelectSquares.get(j).GetX();
				int y2 = SelectSquares.get(j).GetY();
				if (x1 == x2) {
					count_x++;
				}
				if (y1 == y2) {
					count_y++;
				}
			}
		}
		if (!(count_x == 1 && count_y == 1)) {
			return false;

		} else {
			return true;
		}
	}

	/**
	 * �ж�����ɫ����ɫ�Ƿ�һ��
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	private boolean isSameColor(int c1, int c2) {
		if (c1 == c2) {
			return true;
		} else {
			return false;
		}
	}

	private boolean IsSameSquare(Square s1, Square s2) {
		if (s1.GetX() == s2.GetX() && s1.GetY() == s2.GetY()) {
			return true;
		} else {
			return false;
		}
	}

	protected void level_three() {
		Toast.makeText(context, "������", Toast.LENGTH_SHORT).show();
		level++;
		countTime += 180;
	}

	protected void level_two() {
		Toast.makeText(context, "�ڶ���", Toast.LENGTH_SHORT).show();
		level++;
		countTime += 180;
	}

	@Override
	public void onDraw(Canvas canvas) {
		Paint textPaint = new Paint();
		textPaint.setTextSize((float) 40.0);
		textPaint.setColor(color.holo_blue_light);
		textPaint.setAlpha(200);
		textPaint.setStyle(Style.FILL_AND_STROKE);
		int y = this.getHeight() - SquareView.line * this.lengthOfSquare;
		if (this.countTime < 10) {
			state++;
			Paint timePaint = new Paint();
			if (state % 2 == 0) {
				timePaint.setColor(Color.YELLOW);
			} else {
				timePaint.setColor(Color.WHITE);
			}
			canvas.drawRect(this.getWidth() / 2, y - 70, this.getWidth(),
					y - 10, timePaint);
		}
		canvas.drawText("Time:" + Integer.toString(countTime),
				this.getWidth() / 2+10, y - 15, textPaint);
		// /////////��time
		if (this.Flag_Time == 1) {
			textPaint.setColor(Color.RED);
			this.Flag_Time = 0;
		}

		if (this.score > sharedPreferences.getInt("High_Score", 0)) {
			textPaint.setTextSize((float) 22.0);
			textPaint.setColor(Color.RED);
			canvas.drawText("new highest", this.getWidth() / 2, 20, textPaint);
		}
		textPaint.setTextSize(40);
		canvas.drawText("Score:" + Integer.toString(score), 20, y - 15,
				textPaint);

		// Draw level
		textPaint.setTextSize(25);
		canvas.drawText("LEVEL:" + (level - 2) + " " + " colors:" + level, 30,
				22, textPaint);

		if (Squares.size() == 0) {
			initialize();
			Log.d("ccccccc",colors.toString());
		}
		for (int i = 0; i < Squares.size(); i++) {

			int left = Squares.get(i).GetX() - lengthOfSquare / 2;
			int top = Squares.get(i).GetY() - lengthOfSquare / 2;
			int right = left + lengthOfSquare;
			int bottom = top + lengthOfSquare;
			int color = Squares.get(i).GetColor();

			Paint paint = new Paint();
			paint.setStyle(Style.STROKE);// �����ľ�����Ϊ���μ�Ŀ�϶
			paint.setColor(Color.WHITE);
			canvas.drawRect(left, top, right, bottom, paint);

			paint.setStyle(Style.FILL);// ��ʵ�ľ�������ɫ���
			paint.setColor(color);
			canvas.drawRect(left + 2, top + 2, right - 2, bottom - 2, paint);
			if (Squares.get(i).isSelected()) {
				paint.setStyle(Style.FILL);// ��ʵ�ľ�������ɫ���
				paint.setColor(color);
				canvas.drawRect(left -1, top -1, right +1, bottom +1, paint);
				// �����
				paint.setColor(Color.WHITE);
				paint.setStrokeWidth(2);
				canvas.drawLine(right - 3, top + 3, left + 3, bottom - 3, paint);// ����
				canvas.drawLine(left + 3, top + 3, right - 3, bottom - 3, paint);// б��
				canvas.drawCircle((right-left)/2, (bottom-top)/2, 2, paint);
			}

		}
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// Log.d(TAG,
			// "Event.getx:" + event.getX() + "Event.gety:" + event.getY());
			Square selectedSquare = getWhichSelected(event.getX(), event.getY());
			if (selectedSquare == null) {
				// if(event.getX()>this.getWidth()-220 &&
				// event.getX()<this.getWidth()-180 && event.getY()>20 &&
				// event.getY()<60){
				// if(this.refresh_time==0){
				// Toast.makeText(this.context, "refresh������",
				// Toast.LENGTH_SHORT).show();
				// }
				// else{
				// this.refresh_time--;
				// this.Squares.clear();
				// }
				// Log.d("refresh",
				// String.valueOf(event.getX())+"y:"+String.valueOf(event.getY()));
				// Log.d("sizeofsquares", String.valueOf(this.Squares.size()));
				return false;
			}
			if (!selectedSquare.isSelected()) {
				selectedSquare.setSelected(true);
				if (sharedPreferences_s.getBoolean("yxflag", true)) {
					// play music
					play(soundMap.get(1), 0);
				}
				int size = SelectSquares.size();
				switch (size) {
				case 0:
					SelectSquares.add(selectedSquare);
					break;
				case 1:// color�Ƿ���ͬ
					if (identifyColor(selectedSquare)) {
						SelectSquares.add(selectedSquare);
						// Toast.makeText(context, "�ɹ�ѡ������", Toast.LENGTH_SHORT)
						// .show();
					} else {
						selectedSquare.setSelected(false);
						countTime -= 2;
						clearSelectSquares();
						Toast.makeText(context, "��ѡ����ɫһ�µķ���",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 2:
					// �ж���ɫ
					if (identifyColor(selectedSquare)) {
						// �ж��Ƿ����һ��������
						SelectSquares.add(selectedSquare);
						if (isRect_three(selectedSquare)) {
							// Toast.makeText(context, "�ɹ�ѡ������",
							// Toast.LENGTH_SHORT)
							// .show();
						} else {
							clearSelectSquares();
							countTime -= 3;
							Toast.makeText(context, "��ѡ�е��������鲻�ܹ��ɾ���",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						selectedSquare.setSelected(false);
						clearSelectSquares();
						countTime -= 3;
						Toast.makeText(context, "��ѡ����ɫһ�µķ���",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 3:
					// ��ɫ
					if (identifyColor(selectedSquare)) {
						// �ж��Ƿ����һ��������
						SelectSquares.add(selectedSquare);
						if (isRect_four(selectedSquare)) {
							// ��һ���������������Ҫ�����ľ��εı߽�ֵ
							getBoundrayValue();
							// Log.d("minX", String.valueOf(minX));
							// Log.d("minY", String.valueOf(minY));
							// Log.d("maxX", String.valueOf(maxX));
							// Log.d("maxY", String.valueOf(maxY));

							// Toast.makeText(context, "�ɹ�ѡ���ĸ�",
							// Toast.LENGTH_SHORT)
							// .show();
							// ���һ������������������������Σ�����������䣬����÷ֵȵ�
							if (sharedPreferences_s.getBoolean("yxflag", true)) {
								// play music
								play(soundMap.get(2), 0);
							}
							resetSquares(minX, minY, maxX, maxY);

							// ������������
							clearSelectSquares();
						} else {
							clearSelectSquares();
							countTime -= 4;
							Toast.makeText(context, "��ѡ���鲻�ܹ��ɾ���",
									Toast.LENGTH_SHORT).show();
						}

					} else {
						selectedSquare.setSelected(false);// ��ɫ��һ������ȡ���������α�ѡ��״̬
															// ,ͬʱ����ȡ��ѡ��Ч������
						clearSelectSquares();
						Toast.makeText(context, "��ѡ����ɫһ�µķ���",
								Toast.LENGTH_SHORT).show();
					}
					break;
				}
			} else {
				selectedSquare.setSelected(false);
				SelectSquares.remove(selectedSquare);
			}
			Log.d("List size", "List size:" + SelectSquares.size());
			Log.d(TAG, "OnTouchEvent:" + score);
			invalidate();
		}
		return super.onTouchEvent(event);
	}

	public void pause() {
		isTimerStop = true;
		if (playMusic != null) {
			playMusic.pauseBgSound();
		}
		
		new AlertDialog.Builder(context)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("Game Pause")
				.setMessage("Resume game")
				.setCancelable(false)
				.setPositiveButton("Resume",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								isTimerStop = false;
								if (playMusic != null) {
									playMusic.startAgainBgSound();
								}
								
							}
						})
				.setNegativeButton("Close",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (playMusic != null) {
									playMusic.stopBgSound();
								}
								dialog.cancel();
								GameActivity GA = (GameActivity) context;
								GA.finish();
							}
						}).show();
	}

	public void play(int sound, int uLoop) {
		AudioManager mgr = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		// ���ص�ǰAlarmManager�������
		float audioMaxVolume = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		// ���ص�ǰAudioManager���������ֵ
		float audioCurrentVolume = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumnRatio = audioMaxVolume;
		soundPool.play(sound, volumnRatio, // ����������
				volumnRatio, // ����������
				1, // ���ȼ���0Ϊ���
				uLoop, // ѭ��������0�޲�ѭ����-1����Զѭ��
				1);// �ط��ٶȣ�ֵ��0.5-2.0֮�䣬1Ϊ�����ٶ�

	}

	public void refresh() {
		if (sharedPreferences_s.getBoolean("yxflag", true)) {
			// play music
			play(soundMap.get(2), 0);
		}
		Squares.clear();
		InitSquares();
		invalidate();
	}

	private void resetSquares(int minX2, int minY2, int maxX2, int maxY2) {
		for (int i = 0; i < Squares.size(); i++) {
			Square square = Squares.get(i);
			if (square.GetX() >= minX2 && square.GetX() <= maxX2
					&& square.GetY() >= minY2 && square.GetY() <= maxY2) {
				int index = (int) (Math.random() * SquareView.level);
				int color = colors.get(index);
				square.SetColor(color);
				score++;
			}
		}

	}

	// ����˵��(ÿһ�е�һ�����x,y���꣬square�䳤������)
	private void SetEachLine(int x, int y, int length) {
		for (int i = 0; i < column; i++) {
			int index = (int) (Math.random() * SquareView.level);
			int color = colors.get(index);
			Square s = new Square(x, y, color);
			x += length;
			Squares.add(s);
		}
	}



	
	
}
