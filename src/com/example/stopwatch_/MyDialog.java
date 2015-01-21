package com.example.stopwatch_;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class MyDialog extends Dialog{
	Context context;
	public MyDialog(Context context) {
		super(context);
		this.context = context;
	}
	//private ArrayList<String> Usr_infolist = new ArrayList<String>();
	//ArrayAdapter<String> adapter;
	public MyDialog(Context context,final DBadapter DBAdapter,WindowManager m,final ArrayList<String> list,final ArrayAdapter<String> adapter) {
		super(context);
		this.context = context;
		//final Dialog dialog = new Dialog(context);
		setTitle("ADD NEW USER");
		setContentView(R.layout.add_usr);
		Window dialogWindow = getWindow();
		//WindowManager m = getWindowManager();
		DisplayMetrics dm = new DisplayMetrics();
		dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(dm);
		//Display d = m.getDefaultDisplay();
		WindowManager.LayoutParams p = dialogWindow.getAttributes();
		p.height = (int) (dm.heightPixels * 0.7);
		p.width = (int) (dm.widthPixels * 0.9);
		//p.height = (int) (d.getHeight() * 0.7);
		//p.width = (int) (d.getWidth() * 0.9);
		dialogWindow.setAttributes(p);
		final EditText UsrName_et = (EditText)findViewById(R.id.UsrName_et);
		Button add_btn = (Button)findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!UsrName_et.getText().toString().trim().equals("")){
					DBAdapter.insert(UsrName_et.getText().toString());
					list.add(UsrName_et.getText().toString());
					adapter.notifyDataSetChanged();
				}else{
					
					for(int i=0;i<100;i++){
						DBAdapter.insert(i+"");
					}
				}
				cancel();
			}
		});
		setCanceledOnTouchOutside(false);
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setContentView(R.layout.add_usr);
    }
}