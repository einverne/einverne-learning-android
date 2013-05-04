package com.einverne.testsqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bindListView();
		
		
		findViewById(R.id.button_click).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String insertSql = "insert into users (id,name) values(?,?);";
				EditText et1 = (EditText)findViewById(R.id.editText_id);
				String id = et1.getText().toString();
				EditText et2 = (EditText)findViewById(R.id.editText_name);
				String name = et2.getText().toString();
				MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(MainActivity.this);
				SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
				db.execSQL(insertSql, new Object[]{id,name});
				
				mySqliteOpenHelper.close();
				bindListView();
			}
		});
		
		findViewById(R.id.button_update).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String updateSql = "update users set name = ? where id = ?;";
				EditText et1 = (EditText)findViewById(R.id.editText_id);
				String id = et1.getText().toString();
				EditText et2 = (EditText)findViewById(R.id.editText_name);
				String name = et2.getText().toString();
				MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(MainActivity.this);
				SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
				db.execSQL(updateSql, new Object[]{name,id});
				
				mySqliteOpenHelper.close();
				bindListView();
			}
		});
		findViewById(R.id.button_delete).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String deleteSql = "delete from users where id = ?;";
				EditText et1 = (EditText)findViewById(R.id.editText_id);
				String id = et1.getText().toString();
				MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(MainActivity.this);
				SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
				db.execSQL(deleteSql, new Object[]{id});
				
				mySqliteOpenHelper.close();
				bindListView();
			}
		});
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long _id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long _id) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void bindListView() {
		MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(this);
		SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
		String selectSql = "select id as _id,name from users;";
		Cursor cs = db.rawQuery(selectSql, null);
		startManagingCursor(cs);
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setAdapter(new SimpleCursorAdapter(this, 
				R.layout.listview_item, cs, 
				new String[]{"_id","name"},
				new int[]{R.id.textView1,R.id.textView2}));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
