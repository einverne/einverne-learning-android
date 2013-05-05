package com.einverne.testcontent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != RESULT_OK) {
			return;
		}
		Uri uri = data.getData();
		ContentResolver contentResolver = getContentResolver();
		
		String[] columns = {"_id","display_name","has_phone_number"};
		Cursor cursor = contentResolver.query(uri, columns, null, null, null);
		
		while (cursor.moveToNext()) {
			String _id = cursor.getString(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("display_name"));
			int has_phone_number = cursor.getInt(cursor.getColumnIndex("has_phone_number"));
			String phoneNumber = null;
			if (has_phone_number > 0) {
				Uri uri2 = Phone.CONTENT_URI;
				String[] columns2 = {"data1"};
				Cursor cursor2 = contentResolver.query(uri2, columns2, "contact_id = "+_id, null, null);
				 
				while (cursor2.moveToNext()) {
					phoneNumber = phoneNumber+cursor2.getString(cursor2.getColumnIndex("data1"));
				}
			}
			Log.d("content", _id+": "+name+": "+phoneNumber);
		}
		cursor.close();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewData();
			}
		});
		
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			Uri uri ;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uri = Contacts.CONTENT_URI;
				Intent intent = new Intent(Intent.ACTION_PICK,uri);
				startActivityForResult(intent, 100);
			}
		});
	}

	private void viewData() {
		Uri uri = Contacts.CONTENT_URI;
		ContentResolver contentResolver = getContentResolver();
		
		String[] columns = {"_id","display_name","has_phone_number"};
		Cursor cursor = contentResolver.query(uri, columns, null, null, null);
		
		ArrayList list = new ArrayList();
		
//		cursor.moveToFirst();
//		for (int i = 0; i < cursor.getColumnCount(); i++) {
//			String name = cursor.getColumnName(i);
//			String value = cursor.getString(i);
//			Log.d("content", name+": "+value);
//		}
		
		while (cursor.moveToNext()) {
			String _id = cursor.getString(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("display_name"));
			int has_phone_number = cursor.getInt(cursor.getColumnIndex("has_phone_number"));
			String phoneNumber = null;
			if (has_phone_number > 0) {
				Uri uri2 = Phone.CONTENT_URI;
				String[] columns2 = {"data1"};
				Cursor cursor2 = contentResolver.query(uri2, columns2, "contact_id = "+_id, null, null);
				 
				while (cursor2.moveToNext()) {
					phoneNumber = phoneNumber+cursor2.getString(cursor2.getColumnIndex("data1"));
				}
			}
			Log.d("content", _id+": "+name+": "+phoneNumber);
			Map map = new HashMap();
			map.put("_id", _id);
			map.put("display_name", name);
			map.put("phoneNumber", phoneNumber);
			list.add(map);
		}
		cursor.close();
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setAdapter(new SimpleAdapter(MainActivity.this,
				list,
				R.layout.list_item,
				new String[]{"display_name","phoneNumber"},
				new int[]{R.id.textView_name,R.id.textView_number}));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
