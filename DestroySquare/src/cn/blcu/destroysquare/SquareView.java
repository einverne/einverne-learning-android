package cn.blcu.destroysquare;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SquareView extends View {

	private Context context;
	private List<Square> Squares;
	private List<Square> SelectSquare;
	int LengthOfSquare;//����ÿ��С����ı߳�
	
	public SquareView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		iniSquareView(context);
	}


	public SquareView(Context context, AttributeSet attrs) {
		super(context, attrs);
		iniSquareView(context);
	}

	public SquareView(Context context) {
		super(context);
		iniSquareView(context);
	}

	/**
	 * ��ʼ��SquareView
	 * @param context
	 */
	private void iniSquareView(Context context) {
		iniSquaresList();//��ʼ��List
		iniLengthOfSquare();//��ʼ��С����߳�
	}
	
	private void iniLengthOfSquare() {
		// TODO Auto-generated method stub
		
	}


	private void iniSquaresList() {
		// TODO Auto-generated method stub
		
	}

	private Square getWhichSelected(){
		return new Square();
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

}
