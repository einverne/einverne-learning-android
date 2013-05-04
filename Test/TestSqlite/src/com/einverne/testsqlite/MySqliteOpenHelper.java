package com.einverne.testsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	public MySqliteOpenHelper(Context context) {
		super(context, "test.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createTable = "create table users (id int,name varchar(50));";
		db.execSQL(createTable);
		
		String insertSql = "insert into users (id,name) values (1,'einverne');";
		db.execSQL(insertSql);
		
		String insertSql2 = "insert into users (id,name) values(?,?);";
		db.execSQL(insertSql2, new Object[]{2,"lisi"});
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
