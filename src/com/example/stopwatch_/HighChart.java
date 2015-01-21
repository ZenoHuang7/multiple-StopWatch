package com.example.stopwatch_;

import java.util.Arrays;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class HighChart extends Activity {
	static final String TAG = "HighChart";
	private WebView mWebview;
	private DBadapter DBAdapter;
	String[] name;
	String[] allGrace;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highchart);
        DBAdapter = new DBadapter(getApplicationContext());
        mWebview = (WebView) findViewById(R.id.webview);

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebChromeClient(new WebChromeClient());
        mWebview.loadUrl("file:///android_asset/test.html");
        //mWebview.loadUrl("file:///android_asset/index.html");

        mWebview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //mWebview.loadUrl("javascript:plot([0,1,2,3,4,5,6,7,8,9],['S1','S2','S3','S4','S5','S6','S7','S8','S9'])");
                //mWebview.loadUrl("javascript:createChart()");
            	mWebview.loadUrl("javascript:plot("+Arrays.deepToString(allGrace)+","+Arrays.deepToString(name)+")");
            }
        });
        
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();//¨ú±oBundle
        HighChartData(bundle.getString("Date"));
	}
	private void HighChartData(String SelectedDate){
		Cursor cursor = DBAdapter.getGraceOfDay(SelectedDate);
		cursor.moveToFirst();
		String grace;
		name = new String[cursor.getCount()];
		allGrace = new String[cursor.getCount()];
		for(int i=0;i<cursor.getCount();i++){
			grace = cursor.getString(cursor.getColumnIndex("Grace"));
			allGrace[i] = grace;
			name[i] = "'"+cursor.getString(cursor.getColumnIndex("UsrName"))+"'";
			//Log.e(TAG,cursor.getString(cursor.getColumnIndex("_id"))+" , "+ name[i]+ " , "+grace+ " , "+ cursor.getString(cursor.getColumnIndex("UsrName")));
			cursor.moveToNext();
		}
	}
}