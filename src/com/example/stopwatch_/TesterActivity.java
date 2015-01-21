package com.example.stopwatch_;

import java.util.ArrayList;
import java.util.Iterator;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class TesterActivity extends ListActivity {
	static final String TAG = "TesterActivity";
	private TesterAdapter myadapter;
	private ArrayList<Integer> selectedViewList = new ArrayList<Integer>();
	private ArrayList<String> Usr_infolist = new ArrayList<String>();
	private DBadapter DBAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		DBAdapter = new DBadapter(getApplicationContext());
		Cursor cursor = DBAdapter.getAllUsrName();
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			Usr_infolist
					.add(cursor.getString(cursor.getColumnIndex("UsrName")));
			Log.v(TAG, cursor.getString(cursor.getColumnIndex("UsrName")));
			cursor.moveToNext();
		}
		cursor.close();
		myadapter = new TesterAdapter(this, Usr_infolist);
		setListAdapter(myadapter);
	}
	public void onDestory() {
		super.onDestroy();
		DBAdapter.close();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tester, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		int index;
		/*
		 * if (id == R.id.action_settings) { return true; }
		 */
		if (id == R.id.clcik_btn) {

			Log.v(TAG, selectedViewList + "");
			for (int i = 0; i < selectedViewList.size(); i++) {
				index = selectedViewList.get(i);
				if (!myadapter.statusChronometer(index)) {
					myadapter.startChronometer(index);
				} else {
					// myadapter.restartChronometer(index);
					myadapter.setRecord(index);
				}
				myadapter.cancelCheck(index);
			}
			selectedViewList.clear();

			return true;
		}
		if (id == R.id.finish_btn) {
			for (int i = 0; i < selectedViewList.size(); i++) {
				index = selectedViewList.get(i);
				if (myadapter.statusChronometer(index)) {
					myadapter.stopChoronometer(index);
					// 記名
					myadapter.setFinish(index);
					// 加上秒數
					myadapter.setAddTime(index);
					// 結束加入成績
					myadapter.setRecord(index);
				}
				myadapter.cancelCheck(index);
			}
			selectedViewList.clear();

			return true;
		}
		if (id == R.id.reset_btn) {

			for (int i = 0; i < selectedViewList.size(); i++) {
				index = selectedViewList.get(i);
				myadapter.resetChronometer(index);
				myadapter.cancelCheck(index);
			}
			selectedViewList.clear();

			return true;
		}
		if (id == R.id.selectAll_btn) {
			myadapter.setSelectedAll(selectedViewList);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (myadapter.setCheckBox(v, position)) {
			selectedViewList.add(position);
		} else {
			Log.v(TAG, "REMOVE " + selectedViewList.size());
			Iterator<Integer> sListIterator = selectedViewList.iterator();
			while (sListIterator.hasNext()) {
				Integer i = sListIterator.next();
				if (i == position)
					sListIterator.remove();
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Save");
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			dialog.setCancelable(false);
			dialog.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(TesterActivity.this, "Save",
									Toast.LENGTH_SHORT).show();
							myadapter.saveData(DBAdapter);
							Intent myIntent = new Intent();
							myIntent = new Intent(TesterActivity.this, MainActivity.class);
							startActivity(myIntent);
							TesterActivity.this.finish();
						}
					});
			dialog.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent myIntent = new Intent();
							myIntent = new Intent(TesterActivity.this, MainActivity.class);
							startActivity(myIntent);
							TesterActivity.this.finish();
						}
					});
			dialog.show();
			
		}
		return super.onKeyDown(keyCode, event);
	}
}
