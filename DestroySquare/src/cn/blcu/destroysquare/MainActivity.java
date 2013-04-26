package cn.blcu.destroysquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageButton start=(ImageButton) findViewById(R.id.start);
		ImageButton help=(ImageButton) findViewById(R.id.help);
		ImageButton setting=(ImageButton) findViewById(R.id.setting);
		
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GameActivity.class);
				startActivity(intent);
				MainActivity.this.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);//参数:读取res中的XML文件实现效果
			}
		});
		
		help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, HelpActivity.class);
				startActivity(intent);
				
			}
		});
		
		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingsActivity.class);
				startActivity(intent);
				
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
