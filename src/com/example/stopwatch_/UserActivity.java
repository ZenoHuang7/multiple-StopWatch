package com.example.stopwatch_;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends ListActivity {
	static final String TAG = "UserActivity";
	private DBadapter DBAdapter;
	private ArrayList<String> Usr_infolist = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		DBAdapter = new DBadapter(getApplicationContext());
		Cursor cursor = DBAdapter.getAllUsrName();
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			Usr_infolist
					.add(cursor.getString(cursor.getColumnIndex("UsrName")));
			cursor.moveToNext();
		}
		cursor.close();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Usr_infolist);
		setListAdapter(adapter);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final String name = ((TextView) view).getText().toString();
				Log.v(TAG, "name = " + name);

				AlertDialog.Builder dialog = new AlertDialog.Builder(
						UserActivity.this);
				dialog.setTitle("Delete");
				dialog.setIcon(android.R.drawable.ic_dialog_alert);
				dialog.setCancelable(false);
				dialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Toast.makeText(UserActivity.this, "del "+name,
										Toast.LENGTH_SHORT).show();
								 DBAdapter.DeleteOneUser(name);
								 Usr_infolist.remove(name);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.addUsr_btn) {
			MyDialog dialog = new MyDialog(UserActivity.this,DBAdapter,getWindowManager(),Usr_infolist,adapter);
			dialog.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}
}