package com.example.stopwatch_;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GraceListActivity extends ListActivity {
	static final String TAG = "GraceListActivity";
	private DBadapter DBAdapter;
	private ArrayList<String> Datelist = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		DBAdapter = new DBadapter(getApplicationContext());
		Cursor cursor = DBAdapter.getDateList();
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			Log.v(TAG, cursor.getString(cursor.getColumnIndex("GraceDate")));
			Datelist.add(cursor.getString(cursor.getColumnIndex("GraceDate")));
			// Log.v(TAG, cursor.getString(cursor.getColumnIndex("UsrName")));
			cursor.moveToNext();
		}
		cursor.close();
		// GraceListAdapter = new TesterAdapter(this, Datelist);
		// setListAdapter(myadapter);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Datelist);
		setListAdapter(adapter);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final String date = ((TextView) view).getText().toString();
				Log.v(TAG, "data = " + date);

				AlertDialog.Builder dialog = new AlertDialog.Builder(
						GraceListActivity.this);
				dialog.setTitle("Delete");
				dialog.setIcon(android.R.drawable.ic_dialog_alert);
				dialog.setCancelable(false);
				dialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(GraceListActivity.this, "del "+date,
										Toast.LENGTH_SHORT).show();
								DBAdapter.DeleteOneGrace(date);
								Datelist.remove(date);
								adapter.notifyDataSetChanged();
							}
						});
				dialog.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				dialog.show();
				return true;
			}
		});

	}

	public void onDestory() {
		super.onDestroy();
		DBAdapter.close();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		// Toast.makeText(this, "You selected: " + keyword,
		// Toast.LENGTH_LONG).show();
		// HighChartData(keyword);
		goHighChart(keyword);
	}

	private void goHighChart(String Date) {
		Bundle bundle = new Bundle();
		bundle.putString("Date", Date);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(GraceListActivity.this, HighChart.class);
		startActivity(intent);
	}
}