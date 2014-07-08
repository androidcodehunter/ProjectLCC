package com.project.lcc.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebResultActivity extends Activity {
	private static final String URL = "http://appsomehow.com.wbm2.my-hosting-panel.com/lccservice";
	private WebView webview;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_result);

		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setGeolocationEnabled(true);
		webview.getSettings().setDefaultTextEncodingName("utf-8");
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview.loadUrl(URL);

	}

}
