package com.example.stopwatch_;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBadapter {
	public static final String TAG = "DBadapter";
	public static final String _UsrTable = "UsrTable";
	private static final  String _UsrName = "UsrName";
	public static final String _GraceTable = "GraceTable";
	
	private static final String KEY_ROWID = "_id";
	private static final String _GraceDate = "GraceDate";
	private static final String _Grace = "Grace";
	public static String Create_Table = "CREATE TABLE IF NOT EXISTS " + _UsrTable + " ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + _UsrName
			+ " VARCHAR(60) "  + ");";
	public static String Create_Grace = "CREATE TABLE IF NOT EXISTS " + _GraceTable + " ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + _UsrName
			+ " VARCHAR(60), "  + _GraceDate
			+ " DATETIME, " + _Grace
			+ " TEXT " + ");";
	private SQLiteDatabase db;
	
	public DBadapter(Context context) {
        db = DataBase.getDatabase(context);
    }
    public void close() {
        db.close();
    }
    public boolean insert(String name) {
        ContentValues cv = new ContentValues();     
 
        cv.put(_UsrName, name);
        /*
        cv.put(DATETIME_COLUMN, item.getDatetime());
        cv.put(COLOR_COLUMN, item.getColor().parseColor());
        cv.put(TITLE_COLUMN, item.getTitle());
        cv.put(CONTENT_COLUMN, item.getContent());
        cv.put(FILENAME_COLUMN, item.getFileName());
        cv.put(LATITUDE_COLUMN, item.getLatitude());
        cv.put(LONGITUDE_COLUMN, item.getLongitude());
        cv.put(LASTMODIFY_COLUMN, item.getLastModify());
 		*/
        
        db.insert(_UsrTable, null, cv);
        return true;
    }
    public long insert_grace(HashMap<String, Object> data){
    	ContentValues initialValues = createContentValues(data);
		return db.insert(_GraceTable, null, initialValues);
    }
    public Cursor getAllUsrName() {
		return db.query(_UsrTable, new String[] { KEY_ROWID,
				_UsrName}, null, null, null, null, null);
	}
    public Cursor getGraceOfDay(String seletedDay){
    	return db.query(_GraceTable, new String[] { KEY_ROWID,
				_UsrName,_GraceDate,_Grace}, "datetime("+_GraceDate+")=datetime('"+seletedDay+"')", null, null, null, null);
    }
    public Cursor getDateList(){
    	return db.query(_GraceTable, new String[] { KEY_ROWID,_GraceDate}, null , null, _GraceDate, null, null);
    }
    public Cursor getGraceCount(){
    	return db.query(_GraceTable, new String[] { KEY_ROWID}, null, null, null, null, null);
    }
    public int getCount(){
    	Cursor cursor = db.query(_UsrTable, new String[] { KEY_ROWID,
				_UsrName}, null, null, null, null, null);
    	int feedback = cursor.getCount();
    	cursor.close();
    	return feedback;
    }
    public int DeleteInfo(){
    	return db.delete(_UsrTable, null, null);
    }
    public int DeleteGrace(){
    	return db.delete(_GraceTable, null, null);
    }
    public int DeleteOneGrace(String date){
    	return db.delete(_GraceTable, _GraceDate+"='"+date+"'", null);
    }
    public int DeleteOneUser(String name){
    	return db.delete(_UsrTable, _UsrName+"='"+name+"'", null);
    }
    private ContentValues createContentValues(HashMap<String, Object> datalist) {
		ContentValues values = new ContentValues();
		Iterator<Entry<String, Object>> it = datalist.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) it
					.next();
			values.put(pairs.getKey(), (String) pairs.getValue());
			Log.v(TAG, pairs.getKey()+" , "+ (String) pairs.getValue());
		}

		return values;
	}
}