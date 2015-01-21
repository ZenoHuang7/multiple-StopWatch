package com.example.stopwatch_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	static final String TAG = "MainActivity";
	private Button training_btn,addUsr_btn,grace_btn;
	private DBadapter DBAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DBAdapter = new DBadapter(getApplicationContext());
		
		training_btn =(Button)findViewById(R.id.traing_btn);
		addUsr_btn =(Button)findViewById(R.id.addUsr_btn);
		grace_btn =(Button)findViewById(R.id.grace_btn);
		training_btn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,TesterActivity.class);
				startActivity(intent);
				//finish();
			}
		});
		addUsr_btn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				//MyDialog dialog = new MyDialog(MainActivity.this,DBAdapter,getWindowManager());
				//dialog.show();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,UserActivity.class);
				startActivity(intent);
			}
		});
		grace_btn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,GraceListActivity.class);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.clean_db) {
			DBAdapter.DeleteInfo();
			DBAdapter.DeleteGrace();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
