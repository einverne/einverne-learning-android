package com.einverne.testlistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	List list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list = new ArrayList();
		Map map = new HashMap();
		map.put("name", "einverne");
		map.put("age", "18");
		map.put("img", R.drawable.ic_launcher);
		list.add(map);
		Map map2 = new HashMap();
		map2.put("name", "zhangsan");
		map2.put("age", "13");
		map2.put("img", R.drawable.ic_launcher);
		list.add(map2);
		ListView lv = (ListView)findViewById(R.id.listView1);
		
		lv.setAdapter(new SimpleAdapter(MainActivity.this,
				list,
				R.layout.list_item_main,
				new String[] {"img","name","age"},
				new int[] {R.id.imageView1,R.id.textView1,R.id.textView2}));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long id) {
				// TODO Auto-generated method stub
				Map map = (Map)list.get(index);
				
				Toast.makeText(MainActivity.this, (String)map.get("name"), Toast.LENGTH_LONG).show();
				
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
