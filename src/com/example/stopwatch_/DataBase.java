package com.example.stopwatch_;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
	// 資料庫名稱
    public static final String DATABASE_NAME = "stopWatch.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;    
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;
    
	public DataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new DataBase(context, DATABASE_NAME, 
                    null, VERSION).getWritableDatabase();
        }
 
        return database;
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		String SQL = "CREATE TABLE IF NOT EXISTS " + _UsrTable + " ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + _UsrName
				+ " VARCHAR(60) "  + ");";
		db.execSQL(SQL);
		*/
		db.execSQL(DBadapter.Create_Table);
		db.execSQL(DBadapter.Create_Grace);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DBadapter._UsrTable);
		db.execSQL("DROP TABLE IF EXISTS " + DBadapter._GraceTable);
		onCreate(db);
	}
	
}