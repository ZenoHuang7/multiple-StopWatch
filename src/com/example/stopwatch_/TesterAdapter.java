package com.example.stopwatch_;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TesterAdapter extends BaseAdapter {
	static final String TAG = "TesterAdapter";
	private LayoutInflater adapterLayoutInflater;
	private ArrayList<TempTagView> rankListArray = new ArrayList<TempTagView>();
	private ArrayList<String> list = null;
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer,TempTagView> bookView = new HashMap<Integer,TempTagView>();
	public TesterAdapter(Context c,ArrayList<String> list){
		adapterLayoutInflater = LayoutInflater.from(c);
		this.list = list;
		for(int i=0;i<list.size();i++){
			Mychronometer chronometer = new Mychronometer(c);
			TempTagView tempTagView = new TempTagView(chronometer,list.get(i));
			bookView.put(i, tempTagView);
		}
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		TagView tag;
		if(view == null){
			view = adapterLayoutInflater.inflate(R.layout.tester_listview, parent, false);
			tag = new TagView(
					(CheckBox)view.findViewById(R.id.checkBox),
					(TextView)view.findViewById(R.id.tester_name_tv),
					(TextView)view.findViewById(R.id.extra_sec_tv),
					(LinearLayout)view.findViewById(R.id.clock_layout));
			view.setTag(tag);
			TempTagView TmpTag = bookView.get(position);
			TmpTag.chronometer.setOnChronometerTickListener(new Mychronometer.OnChronometerTickListener() {
				@Override  
                public void onChronometerTick(Mychronometer chronometer) {  
					//Log.v(TAG,chronometer.getText().toString());
				}
			});
			tag.tester_name_tv.setText(TmpTag.tester_name);
			tag.extra_sec_tv.setText(TmpTag.extra_sec);
			tag.checkBox.setChecked(TmpTag.check);
			tag.clockLayout.removeAllViews();
			tag.clockLayout.addView(TmpTag.chronometer);
			
		}
		else{
			tag = (TagView)view.getTag();
		}
		if(bookView.containsKey(position)){
			TempTagView TmpTag = bookView.get(position);
			tag.tester_name_tv.setText(TmpTag.tester_name);
			tag.extra_sec_tv.setText(TmpTag.extra_sec);
			tag.checkBox.setChecked(TmpTag.check);
			tag.clockLayout.removeAllViews();
			tag.clockLayout.addView(TmpTag.chronometer);
		}
		return view;
	}
	public class TempTagView{
		String tester_name,extra_sec="0";
		boolean check = false,flag = false;
		Mychronometer chronometer;
		private ArrayList<Long> recoredTime = new ArrayList<Long>(); 
		public TempTagView(Mychronometer c,String name){
			this.chronometer = c;
			this.tester_name = name;
		}
	}
	public class TagView{
		CheckBox checkBox;
		TextView tester_name_tv,extra_sec_tv;
		Chronometer chronometer;
		LinearLayout clockLayout;
		public TagView(CheckBox CheckBox, TextView UserTextView,
				TextView GraceTextView, LinearLayout clockLayout) {
			this.checkBox = CheckBox;
			this.tester_name_tv = UserTextView;
			this.extra_sec_tv = GraceTextView;
			this.clockLayout = clockLayout;
		}
	}
	public boolean statusChronometer(int index){
		return bookView.get(index).flag;
	}
	public void restartChronometer(int index){
		bookView.get(index).chronometer.setBase(SystemClock.elapsedRealtime());
		bookView.get(index).chronometer.start();
		bookView.get(index).flag = true;
		bookView.put(index, bookView.get(index));
	}
	public void startChronometer(int index){
		bookView.get(index).chronometer.setBase(SystemClock.elapsedRealtime());
		bookView.get(index).chronometer.start();
		bookView.get(index).flag = true;
		bookView.put(index, bookView.get(index));
	}
	public void stopChoronometer(int index){
		bookView.get(index).chronometer.stop();
		bookView.get(index).flag = false;
		bookView.put(index, bookView.get(index));
	}
	public void resetChronometer(int index){
		bookView.get(index).chronometer.stop();
		bookView.get(index).chronometer.setBase(SystemClock.elapsedRealtime());
		bookView.get(index).extra_sec = "0";
		bookView.get(index).flag = false;
		bookView.put(index, bookView.get(index));
		//§PÂ_¥þ³¡°±¤î
		boolean c = false;
		for (Object key : bookView.keySet()) {
			if(bookView.get(key).flag){
				c = true;
				break;
			}
		}
		if(!c)rankListArray.clear();
	}
	protected long convertStrTimeToLong(String strTime) {
	    String []timeArry=strTime.split(":");
	    long longTime=0;
	    if (timeArry.length==2) {
	        longTime=Integer.parseInt(timeArry[0])*1000*60+Integer.parseInt(timeArry[1])*1000;
	    }else if (timeArry.length==3){
	        longTime=Integer.parseInt(timeArry[0])*1000*60*60+Integer.parseInt(timeArry[1])
	              *1000*60+Integer.parseInt(timeArry[0])*1000;
	    }            
	    return SystemClock.elapsedRealtime()-longTime;
	}
	public boolean setCheckBox(View v,int index){
		TagView tag = (TagView)v.getTag();
		if(!bookView.get(index).check){
			bookView.get(index).check =true;
			bookView.put(index, bookView.get(index));
			tag.checkBox.setChecked(true);
		}
		else{
			bookView.get(index).check =false;
			bookView.put(index, bookView.get(index));
			tag.checkBox.setChecked(false);
		}
		return bookView.get(index).check;
	}
	public void setFinish(int index){
		rankListArray.add(bookView.get(index));
	}

	public void setRecord(int index){
		long record = Math.abs(convertStrTimeToLong(bookView.get(index).chronometer.getText().toString())-convertStrTimeToLong("00:00"))/1000;
		bookView.get(index).recoredTime.add(record);
	}
	public void setAddTime(int index){
		TempTagView first_tag = rankListArray.get(0);
		long need_add = Math.abs(convertStrTimeToLong(bookView.get(index).chronometer.getText().toString()) - convertStrTimeToLong(first_tag.chronometer.getText().toString()))/1000;
		bookView.get(index).extra_sec = "+"+need_add;
		this.notifyDataSetChanged();
	}
	public void setSelectedAll(ArrayList<Integer> adapter){
		adapter.clear();
		for (Object key : bookView.keySet()) {
			TempTagView tmptag = bookView.get(key);
			tmptag.check = true;
			bookView.put((Integer)key, bookView.get(key));
			adapter.add((Integer)key);
        }
		this.notifyDataSetChanged();
	}
	public void cancelCheck(int index){
		bookView.get(index).check = false;
		bookView.put(index, bookView.get(index));
		this.notifyDataSetChanged();
	}
	public boolean saveData(DBadapter DBAdapter){
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
	            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		for (Object key : bookView.keySet()) {
			TempTagView tmptag = bookView.get(key);
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.clear();
			if(tmptag.recoredTime.size()!=0){
				String grace = tmptag.recoredTime.toString();
				String dateTime = dateFormat.format(date);
				data.put("UsrName", tmptag.tester_name);
				data.put("GraceDate", dateTime);
				data.put("Grace", grace);
				DBAdapter.insert_grace(data);
				//Log.v(TAG,re+"db.save = "+ tmptag.tester_name);
			}
        }
		return true;
	}
}
