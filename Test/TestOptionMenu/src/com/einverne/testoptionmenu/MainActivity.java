package com.einverne.testoptionmenu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		
		menu.add(Menu.NONE, 1, 1, "First");
		menu.add(Menu.NONE, 2, 2, "Second");
		menu.add(Menu.NONE, 3, 3, "Third");
		
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, item.getItemId()+"", Toast.LENGTH_LONG).show();
		return super.onOptionsItemSelected(item);
	}
}
