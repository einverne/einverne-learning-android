package cn.blcu.destroysquare;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.blcu.destroysquaretool.DateTool;

import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity implements View.OnClickListener {

	private static final int HELP = 1;
	private static final int SETTING = 2;
	private static final int START = 0;
	private static final String TAG = "EV_DEBUG";
	ImageButton help;
	ImageButton setting;
	ImageButton start;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case START:
			// ����spendPoints����ָ�����Ļ��֣�����ʾ������100����
			if (PointsManager.getInstance(this).spendPoints(10)) {
				int myPointBalance = PointsManager.getInstance(this)
						.queryPoints();
				Log.d("test", "������10���֣���ǰ���Ϊ:" + myPointBalance);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GameActivity.class);
				startActivity(intent);
				MainActivity.this.overridePendingTransition(R.anim.rotate_in,
						R.anim.rotate_out);// ����:��ȡres�е�XML�ļ�ʵ��Ч��
				// ҡ��
			} else {
				Toast.makeText(MainActivity.this, "���ѻ���ʧ��(��������)",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case HELP:
			Intent intent_2 = new Intent();
			intent_2.setClass(MainActivity.this, HelpActivity.class);
			startActivity(intent_2);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);
			;// ����:��ȡres�е�XML�ļ�ʵ��Ч��
			break;
		case SETTING:
			Intent intent_3 = new Intent();
			intent_3.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent_3);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);// ����:��ȡres�е�XML�ļ�ʵ��Ч��
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��ʼ��Ӧ�õķ���ID����Կ���Լ����ò���ģʽ
		AdManager.getInstance(this).init("fe6bc463b69db90c",
				"6d8d1e9b776c114b", false);
		// ����ص������´��룬����SDKӦ��������������SDK����һЩ��ʼ��������
		OffersManager.getInstance(this).onAppLaunch();
		// ����showOffersWall��ʾȫ���Ļ���ǽ����
		start = (ImageButton) findViewById(R.id.start);
		start.setTag(START);
		help = (ImageButton) findViewById(R.id.help);
		help.setTag(HELP);
		setting = (ImageButton) findViewById(R.id.setting);
		setting.setTag(SETTING);
		start.setOnClickListener(this);
		help.setOnClickListener(this);
		setting.setOnClickListener(this);

		iniADs();
		sharedPreferences = this.getSharedPreferences("Date",
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		if (sharedPreferences.getString("isFirst", "first").equals("first")) {
			// ����awardPoints���Խ���ָ�����Ļ��֣�����ʾ������50����
			PointsManager.getInstance(this).awardPoints(50);
			int myPointBalance = PointsManager.getInstance(this)
					.queryPoints();
			Toast.makeText(this, "����50���֣���ǰ���:" + myPointBalance,
					Toast.LENGTH_SHORT).show();
			editor.putString("isFirst", "not");
			editor.commit();
		}
	}

	private void iniADs() {
		//ʵ���������
		AdView adView = new AdView(this, AdSize.SIZE_320x50);
		//��ȡҪ������Ĳ���
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		//����������뵽������
		adLayout.addView(adView);
	}

	@Override
	protected void onStart() {
		EasyTracker.getInstance().activityStart(this);
		super.onStart();
	}

	@Override
	protected void onStop() {
		EasyTracker.getInstance().activityStop(this);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// �������Ӧ���˳���ʱ��������´��룬����SDKӦ���Ѿ��رգ�������SDK����һЩ��Դ���ͷź�����
		OffersManager.getInstance(this).onAppExit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent_3 = new Intent();
			intent_3.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent_3);
			MainActivity.this.overridePendingTransition(R.anim.rotate_in,
					R.anim.rotate_out);// ����:��ȡres�е�XML�ļ�ʵ��Ч��
			break;
		case R.id.action_eachday:

			if (!DateTool.getTodayDate().equals(
					sharedPreferences.getString("date",
							DateTool.getDateTodayMinusDay(1)))) {
				// ����awardPoints���Խ���ָ�����Ļ��֣�����ʾ������50����
				PointsManager.getInstance(this).awardPoints(50);
				int myPointBalance = PointsManager.getInstance(this)
						.queryPoints();
				Toast.makeText(this, "����50���֣���ǰ���:" + myPointBalance,
						Toast.LENGTH_SHORT).show();
				Log.d("EV_DEBUG", "�ѳɹ�����50���֣���ǰ���Ϊ:" + myPointBalance);
				editor.putString("date", DateTool.getTodayDate());
				editor.commit();
			} else {
				Toast.makeText(this, "�����Ѿ�ǩ����,����������!", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.action_jifen:
			// ����showOffersWall��ʾȫ���Ļ���ǽ����
			OffersManager.getInstance(this).showOffersWall();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
