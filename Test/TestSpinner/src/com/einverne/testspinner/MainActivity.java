package com.einverne.testspinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	List list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list = new ArrayList();
		Map map1 = new HashMap();
		map1.put("img", R.drawable.ic_launcher);
		map1.put("font", "宋体");
		list.add(map1);
		Map map2 = new HashMap();
		map2.put("img", R.drawable.ic_launcher);
		map2.put("font", "楷体");
		list.add(map2);
		
//		String[] strs = {"宋体","楷体","黑体"};
		Spinner spinner = (Spinner)findViewById(R.id.spinner1);
//		spinner.setAdapter(new ArrayAdapter(MainActivity.this,
//				android.R.layout.simple_spinner_item,
//				android.R.id.text1,
//				strs));
		
		spinner.setAdapter(new SimpleAdapter(MainActivity.this,
				list,
				R.layout.spinner_item,
				new String[]{"img","font"},
				new int[]{R.id.imageView1,R.id.textView1}));
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "changed", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
