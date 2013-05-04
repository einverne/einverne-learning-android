package com.einverne.testbitmap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	Bitmap alterBmp;
	private String TAG = "Debug";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button_select).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Images.Media.EXTERNAL_CONTENT_URI;
				Intent intent = new Intent(Intent.ACTION_PICK,uri);
				startActivityForResult(intent, 100);
			}
		});
		
		findViewById(R.id.button_alter).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bitmap alterBmp2 = Bitmap.createBitmap(alterBmp.getWidth() , alterBmp.getHeight() ,alterBmp.getConfig());
				Canvas canvas = new Canvas(alterBmp2);
				Paint paint = new Paint();
				Matrix matrix = new Matrix();
//				matrix.setValues(new float[]{
//						1,0,0,
//						0,1,0,
//						0,0,1
//				});
//				matrix.setTranslate(50, 50);			//平移
//				matrix.setScale(0.5f, 0.5f);				//缩放
//				matrix.setScale(0.5f, 0.5f, alterBmp.getWidth()/2, alterBmp.getHeight()/2);		//相对于中心点收缩
//				
//				matrix.setRotate(1, alterBmp.getWidth()/2, alterBmp.getHeight()/2);
				
				
//				RectF src = new RectF(50, 50, 100, 100);
//				RectF dst = new RectF(0, 0, 100, 100);
//				matrix.setRectToRect(src, dst, ScaleToFit.FILL);
				
				matrix.setScale(-1, 1);
				matrix.postTranslate(alterBmp.getWidth(), 0);
				
				canvas.drawBitmap(alterBmp, matrix, paint);
				ImageView imageView2 = (ImageView)findViewById(R.id.imageView_alter);
				imageView2.setImageBitmap(alterBmp2);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != RESULT_OK) {
			return;
		}
		Uri uri = data.getData();
		ImageView imageView = (ImageView)findViewById(R.id.imageView_pic);
//		imageView.setImageURI(uri);
		
		ContentResolver contentResolver = getContentResolver();
		String[] columns = {"_data"};
		Cursor cursor = contentResolver.query(uri, columns, null, null, null);
		cursor.moveToFirst();
		String path = cursor.getString(cursor.getColumnIndex("_data"));
		Bitmap bmp = BitmapFactory.decodeFile(path);
		
		alterBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),bmp.getConfig());
		imageView.setImageBitmap(alterBmp);
		Canvas canvas = new Canvas(alterBmp);
		Paint paint = new Paint();
//		canvas.drawBitmap(bmp, 0,0, paint);
		
		Rect src = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
		Rect dst = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
		canvas.drawBitmap(bmp, src, dst,paint);
//		canvas.drawText(TAG, 50, 50, paint);
		
//		for (int i = 0; i < cursor.getColumnCount(); i++) {
//			String name = cursor.getColumnName(i);
//			String value = cursor.getString(i);
//			String info = name + ": "+(value!=null?value:"not value");
//			Log.d(TAG, info);
//		}
		

		super.onActivityResult(requestCode, resultCode, data);
	}

}
