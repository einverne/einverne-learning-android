package com.einverne.testimagesize;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private int displayWidth;
	private int displayHeight;
	private Bitmap bmp;
	private ImageView iv;
	private Button btn_small;
	private Button btn_big;
	private RelativeLayout layout;
	
	private float scaleWidth = 1;
	private float scaleHeight = 1;
	
	private int id = 0;
	private String TAG = "EV_DEBUG";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		iv = (ImageView)findViewById(R.id.imageView_test);
		iv.setImageResource(R.drawable.speedtest_2589260249);
		btn_small = (Button)findViewById(R.id.button_small);
		btn_big = (Button)findViewById(R.id.button_big);
		layout = (RelativeLayout)findViewById(R.id.layout);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		displayWidth = dm.widthPixels;
		displayHeight = dm.heightPixels;
		Log.d(TAG, displayWidth+" "+displayHeight+" ");
		
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.speedtest_2589260249);
		
		btn_small.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				small();
			}
		});
		
		btn_big.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				big();
			}
		});
	}

	private void small() {
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		double scale = 0.8;
		
		scaleWidth = (float)(scaleWidth*scale);
		scaleHeight = (float)(scaleHeight*scale);
		Log.d(TAG, scaleWidth+" "+scaleHeight+" ");
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
		
		if (id == 0) {
			layout.removeView(iv);
		} else {
			layout.removeView((ImageView)findViewById(id));
		}
		id++;
		ImageView imageView = new ImageView(MainActivity.this);
		imageView.setId(id);
		imageView.setImageBitmap(resizeBmp);
		layout.addView(imageView);
		setContentView(layout);
		
		btn_big.setEnabled(true);
		
	}
	
	private void big() {
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		
		double scale = 1.25;
		scaleWidth = (float)(scaleWidth*scale);
		scaleHeight = (float)(scaleHeight*scale);
		Log.d(TAG, scaleWidth+" "+scaleHeight+" ");
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0,  0, bmpWidth, bmpHeight, matrix, true);
		
		if (id == 0) {
			layout.removeView(iv);
		}else {
			layout.removeView((ImageView)findViewById(id));
		}
		id++;
		ImageView imageView = new ImageView(MainActivity.this);
		imageView.setId(id);
		imageView.setImageBitmap(resizeBmp);
		layout.addView(imageView);
		setContentView(layout);
		
		if (scaleWidth*scale*bmpWidth> displayWidth || scaleHeight*scale*bmpHeight > displayHeight) {
			btn_big.setEnabled(false);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
