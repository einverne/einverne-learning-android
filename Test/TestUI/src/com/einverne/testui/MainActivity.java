package com.einverne.testui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		findViewById(R.id.login).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText et1 = (EditText)findViewById(R.id.editText_username);
				Editable editable = et1.getText();
				String username = editable.toString();
				if (username.equals("admin")) {
					Toast.makeText(MainActivity.this, "admin Login Success", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(MainActivity.this, "admin Login Failed", Toast.LENGTH_LONG).show();
				}
				
				EditText et2 = (EditText)findViewById(R.id.editText_pwd);
				Editable editable2 = et2.getText();
				editable2.clear();
				editable2.append("LollllllllllllllLLL");
			}
		});
		
		findViewById(R.id.button_radio).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				RadioButton rbt = (RadioButton)findViewById(R.id.radio0);
//				RadioButton rbt_female = (RadioButton)findViewById(R.id.radio1);
//				if (rbt.isChecked()) {
//					Toast.makeText(MainActivity.this, "Male checked", Toast.LENGTH_LONG).show();
//				} else if(rbt_female.isChecked()){
//					Toast.makeText(MainActivity.this, "Female checked", Toast.LENGTH_LONG).show();
//				}else{
//					Toast.makeText(MainActivity.this, "Undefined checked", Toast.LENGTH_LONG).show();
//				}
				
				
				RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
				switch (rg.getCheckedRadioButtonId()) {
				case R.id.radio0:
					Toast.makeText(MainActivity.this, "Male checked", Toast.LENGTH_LONG).show();
					
					break;
				case R.id.radio1:
					Toast.makeText(MainActivity.this, "Female checked", Toast.LENGTH_LONG).show();
					break;
				case R.id.radio2:
					Toast.makeText(MainActivity.this, "Undefined checked", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		});
		
		RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
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
