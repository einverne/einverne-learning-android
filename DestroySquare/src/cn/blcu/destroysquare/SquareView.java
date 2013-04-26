package cn.blcu.destroysquare;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.R;
import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SquareView extends View {

	private Context context;
	private List<Square> Squares;
	private List<Square> SelectSquares;
	int lengthOfSquare;// ����ÿ��С����ı߳�
	private List<Integer> colors;
	private int score;
	private int countTime = 180;
	private String TAG = "EV_DEBUG";

	private int minX = 0;
	private int maxX = 0;
	private int minY = 0;
	private int maxY = 0;

	private static final int line = 9;
	private static final int column = 7;

	public static final int MSG_GAME_OVER = 1;
	Handler handler;

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
		score = 0;
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_GAME_OVER:
					// GAME OVER;
					// new AlertDialog.Builder(SquareView.this.context)
					// .setTitle("title Game Over")
					// .setIcon(R.drawable.ic_launcher)
					// .setMessage("�÷�")
					// .setCancelable(false)
					// .setView(new TextView(getContext())).show();
					Log.d(TAG, "receive MSG Game Over Score:" + score);
					break;

				default:
					break;
				}
			}

		};
		TimerTask timertask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				postInvalidate();
				if (countTime <= 0) {
					handler.sendEmptyMessage(SquareView.MSG_GAME_OVER);
					return;
				}
				countTime--;
			}
		};
		Timer timer = new Timer();
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
			paint.setStyle(Style.STROKE);// �����ľ�����Ϊ���μ�Ŀ�϶
			paint.setColor(Color.WHITE);
			canvas.drawRect(left, top, right, bottom, paint);

			paint.setStyle(Style.FILL);// ��ʵ�ľ�������ɫ���
			paint.setColor(color);
			canvas.drawRect(left + 2, top + 2, right - 2, bottom - 2, paint);
			if (Squares.get(i).isSelected()) {
				paint.setStyle(Style.FILL);// ��ʵ�ľ�������ɫ���
				paint.setColor(color);
				canvas.drawRect(left + 6, top + 6, right - 6, bottom - 6, paint);
				// �����
				paint.setColor(Color.WHITE);
				paint.setStrokeWidth(2);
				canvas.drawLine(right - 3, top + 3, left + 3, bottom - 3, paint);// ����
				canvas.drawLine(left + 3, top + 3, right - 3, bottom - 3, paint);// б��

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
	 * SelectSquares�������,����ȡ�����������б�ѡ�е�������״̬
	 */
	private void clearSelectSquares() {
		for (int j = 0; j < SelectSquares.size(); j++) {
			SelectSquares.get(j).setSelected(false);
		}
		SelectSquares.clear();
	}

	/**
	 * ����ѡ�е���������ɫ�Ƿ�һ���ĺ���
	 * @param selectedSquare
	 * @return
	 */
	private boolean identifyColor(Square selectedSquare) {
		Square existedSquare = SelectSquares.get(0);
		int color1 = selectedSquare.GetColor();
		int color2 = existedSquare.GetColor();
		boolean b = isSameColor(color1, color2);
		if (b == true) {
			return true;
		} else {
			return false;
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
						clearSelectSquares();
						Toast.makeText(context, "ʧ��ѡ������", Toast.LENGTH_SHORT)
								.show();
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
							Toast.makeText(context, "ʧ��ѡ������",
									Toast.LENGTH_SHORT).show();
						}

					} else {
						selectedSquare.setSelected(false);
						clearSelectSquares();
						Toast.makeText(context, "ʧ��ѡ������", Toast.LENGTH_SHORT)
								.show();
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
							Log.d("minX", String.valueOf(minX));
							Log.d("minY", String.valueOf(minY));
							Log.d("maxX", String.valueOf(maxX));
							Log.d("maxY", String.valueOf(maxY));

							// Toast.makeText(context, "�ɹ�ѡ���ĸ�",
							// Toast.LENGTH_SHORT)
							// .show();
							// ���һ������������������������Σ�����������䣬����÷ֵȵ�
							resetSquares(minX, minY, maxX, maxY);

							// ������������
							clearSelectSquares();
						} else {
							clearSelectSquares();
							Toast.makeText(context, "ʧ��ѡ���ĸ�",
									Toast.LENGTH_SHORT).show();
						}

					} else {
						selectedSquare.setSelected(false);// ��ɫ��һ������ȡ���������α�ѡ��״̬
															// ,ͬʱ����ȡ��ѡ��Ч������
						clearSelectSquares();
						Toast.makeText(context, "ʧ��ѡ���ĸ�", Toast.LENGTH_SHORT)
								.show();
					}
					break;
				}
			} else {
				selectedSquare.setSelected(false);
				SelectSquares.remove(selectedSquare);
			}
			Log.d("List size", "size" + SelectSquares.size());

			invalidate();
		}
		return super.onTouchEvent(event);
	}

	private void resetSquares(int minX2, int minY2, int maxX2, int maxY2) {
		for (int i = 0; i < Squares.size(); i++) {
			Square square = Squares.get(i);
			if (square.GetX() >= minX2 && square.GetX() <= maxX2
					&& square.GetY() >= minY2 && square.GetY() <= maxY2) {
				int index = (int) (Math.random() * this.colors.size());
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

	// ����˵��(ÿһ�е�һ�����x,y���꣬square�䳤������)
	private void SetEachLine(int x, int y, int length) {
		for (int i = 0; i < column; i++) {
			int index = (int) (Math.random() * this.colors.size());
			int color = colors.get(index);
			Square s = new Square(x, y, color);
			x += length;
			Squares.add(s);
		}
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

	private void InitSquareLength() {
		Log.d(TAG, "InitSquareLength");
		int width = this.getWidth();
		this.lengthOfSquare = width / column;
		Log.d("EV_DEBUG:lengthOfSquare", "" + lengthOfSquare);
	}

	private void InitColors() {
		this.colors.add(getResources().getColor(R.color.holo_blue_light));
		this.colors.add(getResources().getColor(R.color.holo_green_light));
		this.colors.add(getResources().getColor(R.color.holo_orange_light));
	}
}
