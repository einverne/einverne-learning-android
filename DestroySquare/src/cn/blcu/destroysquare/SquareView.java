package cn.blcu.destroysquare;

import java.util.ArrayList;
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
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SquareView extends View {


	private SoundPool soundPool; 
	
	static Context context;
	public static final int MSG_GAME_OVER = 1;
	public static final int MSG_GAME_LEVEL_TWO = 2;
	public static final int MSG_GAME_LEVEL_THREE = 3;

	private List<Square> Squares;
	private List<Square> SelectSquares;
	int lengthOfSquare;// 保存每个小方格的边长
	private List<Integer> colors;
	private int score;
	private int countTime = 10;
	private String TAG = "EV_DEBUG";
	private int sound; 
	private int minX = 0;
	private int maxX = 0;
	private int minY = 0;
	private int maxY = 0;
	 boolean soundFlag=true;
	private static final int line = 9;
	private static final int column = 7;

	public static int level = 3; // level to setting how many kind of
	protected static final int SCORE_TWO_LEVEL = 10;
	protected static final int SCORE_THREE_LEVEL = 100;
	boolean level_two = false;
	boolean level_three = false;

	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	Handler handler;
	Timer timer;

	public SquareView(Context context) {
		super(context);
		iniSquareView(context);
	}

	public SquareView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		iniSquareView(context);
	}

	public SquareView(Context context, AttributeSet attrs) {
		super(context, attrs);
		iniSquareView(context);
	}

	/**
	 * 初始化SquareView
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

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_GAME_OVER:
					// GAME OVER;
					int high_score = sharedPreferences.getInt("High_Score", 0);
					Log.d(TAG, "High_Score:" + high_score + " score:" + score);
					if (score > high_score) {
						editor.putInt("High_Score", score);
						editor.commit();
					}
					new AlertDialog.Builder(SquareView.this.context)
							.setTitle("Game Over")
							.setIcon(R.drawable.ic_launcher)
							.setMessage(
									"最高分:"
											+ sharedPreferences.getInt(
													"High_Score", 0) + "\n"
											+ "本次得分:" + score)
							.setPositiveButton("Try again",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											GameActivity GA = (GameActivity) SquareView.context;
											GA.finish();
											Intent intent = new Intent(GA,
													GameActivity.class);
											GA.startActivity(intent);
										}
									})
							.setNegativeButton("Close",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.cancel();
											GameActivity GA = (GameActivity) SquareView.context;
											GA.finish();
										}
									}).setCancelable(false).show();
					Log.d(TAG, "receive MSG Game Over Score:" + score);
					break;
				case MSG_GAME_LEVEL_TWO:
					Toast.makeText(SquareView.context, "第二关",
							Toast.LENGTH_SHORT).show();
					level++;
					countTime += 180;
					Log.d(TAG, "level" + level + " counttime:" + countTime);
					break;
				case MSG_GAME_LEVEL_THREE:
					Toast.makeText(SquareView.context, "第三关",
							Toast.LENGTH_SHORT).show();
					level++;
					countTime += 180;
					Log.d(TAG, "level" + level + " counttime:" + countTime);
					break;
				default:
					break;
				}
			}

		};
		TimerTask timertask = new TimerTask() {

			@Override
			public void run() {
				postInvalidate();
				if (countTime <= 0) {
					handler.sendEmptyMessage(SquareView.MSG_GAME_OVER);
					timer.cancel();
					return;
				}
				countTime--;
				if (score > SCORE_TWO_LEVEL && !level_two) {
					handler.sendEmptyMessage(SquareView.MSG_GAME_LEVEL_TWO);
					level_two = true;
					return;
				}
				if (score > SCORE_THREE_LEVEL && !level_three) {
					handler.sendEmptyMessage(SquareView.MSG_GAME_LEVEL_THREE);
					level_three = true;
					return;
				}
			}
		};
		timer = new Timer();
		timer.schedule(timertask, 0, 1000);

	}

	public void initialize() {
		InitColors();
		InitSquareLength();
		InitSquares();
	}

	@Override
	public void onDraw(Canvas canvas) {
		Paint textPaint = new Paint();
		textPaint.setTextSize((float) 60.0);
		textPaint.setColor(color.holo_blue_light);
		textPaint.setAlpha(200);
		textPaint.setStyle(Style.FILL_AND_STROKE);
		int y = this.getHeight() - this.line * this.lengthOfSquare;
		// Log.d(TAG, "Y-"+y);
		canvas.drawText("Score:" + Integer.toString(score), 20, y - 20,
				textPaint);
		canvas.drawText("Time:" + Integer.toString(countTime),
				this.getWidth() / 2, y - 20, textPaint);
		if (Squares.size() == 0) {
			initialize();
		}
		for (int i = 0; i < Squares.size(); i++) {

			int left = Squares.get(i).GetX() - lengthOfSquare / 2;
			int top = Squares.get(i).GetY() - lengthOfSquare / 2;
			int right = left + lengthOfSquare;
			int bottom = top + lengthOfSquare;
			int color = Squares.get(i).GetColor();

			Paint paint = new Paint();
			paint.setStyle(Style.STROKE);// 画空心矩形作为矩形间的空隙
			paint.setColor(Color.WHITE);
			canvas.drawRect(left, top, right, bottom, paint);

			paint.setStyle(Style.FILL);// 画实心矩形用颜色填充
			paint.setColor(color);
			canvas.drawRect(left + 2, top + 2, right - 2, bottom - 2, paint);
			if (Squares.get(i).isSelected()) {
				paint.setStyle(Style.FILL);// 画实心矩形用颜色填充
				paint.setColor(color);
				canvas.drawRect(left + 6, top + 6, right - 6, bottom - 6, paint);
				// 画打叉
				paint.setColor(Color.WHITE);
				paint.setStrokeWidth(2);
				canvas.drawLine(right - 3, top + 3, left + 3, bottom - 3, paint);// 画线
				canvas.drawLine(left + 3, top + 3, right - 3, bottom - 3, paint);// 斜线

			}

		}
		super.onDraw(canvas);
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

	/**
	 * SelectSquares链表清空,并且取消链表中所有被选中的正方形状态
	 */
	private void clearSelectSquares() {
		for (int j = 0; j < SelectSquares.size(); j++) {
			SelectSquares.get(j).setSelected(false);
		}
		SelectSquares.clear();
	}

	/**
	 * 处理被选中的正方形颜色是否一样的函数
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

	// 判断是否是在一个矩形里面
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Log.d(TAG,
					"Event.getx:" + event.getX() + "Event.gety:" + event.getY());
			Square selectedSquare = getWhichSelected(event.getX(), event.getY());
			if (selectedSquare == null) {
				return false;
			}
			if (!selectedSquare.isSelected()) {
				selectedSquare.setSelected(true);
				if(soundFlag){
				//play music
				play(sound, 0);
				}
				int size = SelectSquares.size();
				switch (size) {
				case 0:
					SelectSquares.add(selectedSquare);
					break;
				case 1:// color是否相同
					if (identifyColor(selectedSquare)) {
						SelectSquares.add(selectedSquare);
						// Toast.makeText(context, "成功选中两个", Toast.LENGTH_SHORT)
						// .show();
					} else {
						selectedSquare.setSelected(false);
						clearSelectSquares();
						Toast.makeText(context, "请选择颜色一致的方块",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 2:
					// 判断颜色
					if (identifyColor(selectedSquare)) {
						// 判断是否组成一个正方形
						SelectSquares.add(selectedSquare);
						if (isRect_three(selectedSquare)) {
							// Toast.makeText(context, "成功选中三个",
							// Toast.LENGTH_SHORT)
							// .show();
						} else {
							clearSelectSquares();
							Toast.makeText(context, "你选中的三个方块不能构成矩形",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						selectedSquare.setSelected(false);
						clearSelectSquares();
						Toast.makeText(context, "请选择颜色一致的方块",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case 3:
					// 颜色
					if (identifyColor(selectedSquare)) {
						// 判断是否组成一个正方形
						SelectSquares.add(selectedSquare);
						if (isRect_four(selectedSquare)) {
							// 做一个函数，计算出需要消除的矩形的边界值
							getBoundrayValue();
							Log.d("minX", String.valueOf(minX));
							Log.d("minY", String.valueOf(minY));
							Log.d("maxX", String.valueOf(maxX));
							Log.d("maxY", String.valueOf(maxY));

							// Toast.makeText(context, "成功选中四个",
							// Toast.LENGTH_SHORT)
							// .show();
							// 添加一个函数消除整个区域的正方形，并且重新填充，计算得分等等
							resetSquares(minX, minY, maxX, maxY);

							// 最后再清空链表
							clearSelectSquares();
						} else {
							clearSelectSquares();
							Toast.makeText(context, "失败选中四个",
									Toast.LENGTH_SHORT).show();
						}

					} else {
						selectedSquare.setSelected(false);// 颜色不一样，则取消该正方形被选中状态
															// ,同时调用取消选中效果函数
						clearSelectSquares();
						Toast.makeText(context, "失败选中四个", Toast.LENGTH_SHORT)
								.show();
					}
					break;
				}
			} else {
				selectedSquare.setSelected(false);
				SelectSquares.remove(selectedSquare);
			}
			Log.d("List size", "List size:" + SelectSquares.size());

			invalidate();
		}
		return super.onTouchEvent(event);
	}

	private void resetSquares(int minX2, int minY2, int maxX2, int maxY2) {
		for (int i = 0; i < Squares.size(); i++) {
			Square square = Squares.get(i);
			if (square.GetX() >= minX2 && square.GetX() <= maxX2
					&& square.GetY() >= minY2 && square.GetY() <= maxY2) {
				int index = (int) (Math.random() * this.level);
				int color = colors.get(index);
				square.SetColor(color);
				score++;
			}
		}

	}

	private boolean IsSameSquare(Square s1, Square s2) {
		if (s1.GetX() == s2.GetX() && s1.GetY() == s2.GetY()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断两个色块颜色是否一致
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

	// 参数说明(每一行第一个点的x,y坐标，square变长，链表)
	private void SetEachLine(int x, int y, int length) {
		for (int i = 0; i < column; i++) {
			int index = (int) (Math.random() * this.level);
			int color = colors.get(index);
			Square s = new Square(x, y, color);
			x += length;
			Squares.add(s);
		}
	}

	/**
	 * 初始化方块list
	 */
	private void InitSquares() {
		int width = lengthOfSquare * column;
		int height = lengthOfSquare * line;
		int screen_width = this.getWidth();
		int screen_height = this.getHeight();
		int offset_x = (screen_width - width) / 2; // 左右居中 , margin
		int offset_y = (screen_height - height - offset_x); // 已经预留了50个像素后上下居中
		// offset_x,offset_y就是第一个Square左上角的坐标
		// ps:保存左上角坐标更方便？？？？？？？？？？？？
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

	private void InitSquareLength() {
		Log.d(TAG, "InitSquareLength");
		int width = this.getWidth();
		this.lengthOfSquare = width / column;
		Log.d("EV_DEBUG:lengthOfSquare", "" + lengthOfSquare);
	}

	private void InitColors() {
		this.colors.add(getResources().getColor(R.color.blue_normal));
		this.colors.add(getResources().getColor(R.color.green_normal));
		this.colors.add(getResources().getColor(R.color.yellow_normal));
		this.colors.add(getResources().getColor(R.color.purple_normal));
		this.colors.add(getResources().getColor(R.color.red_normal));
	}
	
	public void initSounds() { 
	       //初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
	      soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100); 
	       sound = soundPool.load(context, R.raw.yinx, 1);
	     
	      
	  }
	
	 public void play( int sound,  int uLoop) { 
		 AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	        //返回当前AlarmManager最大音量
	        float audioMaxVolume = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	        //返回当前AudioManager对象的音量值
	        float audioCurrentVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	         float volumnRatio = audioMaxVolume;
	            	 soundPool.play(
	            			 sound,   
	     		    		volumnRatio, //左声道音量
	     	                volumnRatio, //右声道音量
	     	                1, //优先级，0为最低
	     	                uLoop, //循环次数，0无不循环，-1无永远循环
	     	                1);//回放速度，值在0.5-2.0之间，1为正常速度
	     
		
		   
		  }
}
