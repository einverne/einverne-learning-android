package com.einverne.testchecknet;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	
	String QQ = "865172453";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		checkNet();
		checkNetworkInfo();
		GetURL();
	}

	public boolean checkNet() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkinto = manager.getActiveNetworkInfo();
		if (networkinto == null || networkinto.isAvailable() == false) {
			new AlertDialog.Builder(this).setMessage("meiyou")
					.setPositiveButton("OK", null).show();
			return false;
		}
		new AlertDialog.Builder(this).setMessage("可以使用")
				.setPositiveButton("OK", null).show();
		return true;
	}

	public void checkNetworkInfo() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		new AlertDialog.Builder(this).setMessage(mobile.toString())
				.setPositiveButton("3G", null).show();

		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		new AlertDialog.Builder(this).setMessage(wifi.toString())
				.setPositiveButton("wifi", null).show();
	}

	public void GetURL() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpClient httpclient = new DefaultHttpClient(httpParams);

		HttpGet get = new HttpGet("http://www.webxml.com.cn/weibservise/qqOnlineWebService.asmx/qqCheckOnline?qqCode="+QQ);
		HttpResponse response = null;
		try {
			response = httpclient.execute(get);

			if (response.getStatusLine().getStatusCode() == 200) {
				byte[] b = EntityUtils.toByteArray(response.getEntity());
				String isLogin = new String(b, "utf-8");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
