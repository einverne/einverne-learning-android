package com.einverne.testdownloadpic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		iv = (ImageView)findViewById(R.id.imageView1);
		String url = "http://f.hiphotos.baidu.com/album/pic/item/aa64034f78f0f7366a7c4bdf0a55b319eac41369.jpg?psign=6a7c4bdf0a55b319ebc4b74543a98226cefc1e178b823751";
		
		new setImageViewData().execute(url);
	}

	/**
	 * 
	 * “Ï≤Ωº”‘ÿÕº∆¨
	 *
	 */
	public class setImageViewData extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			Log.d("EV_DEBUG", "setImageViewData doInBackground");
			return getBitmap(params[0]);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			Log.d("EV_DEBUG", "setImageViewData onPostExecute");
			iv.setImageBitmap(bitmap);
		}

		@Override
		protected void onPreExecute() {
			Log.d("EV_DEBUG", "setImageViewData onPreExecute");
		}
	}
	
	/**
	 * get url and return Bitmap
	 * @param url
	 * @return Bitmap
	 */
	public Bitmap getBitmap(String url){
		URL myFileurl = null;
		Bitmap bitmap = null;
		try {
			myFileurl = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		try {
			HttpURLConnection  httpURLConnection = (HttpURLConnection) myFileurl.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.connect();
			InputStream inputStream = httpURLConnection.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
