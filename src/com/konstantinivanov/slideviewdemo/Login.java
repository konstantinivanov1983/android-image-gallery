package com.konstantinivanov.slideviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Login extends Activity {

	private String AccessTokenURL;
	
	private final String CLIENT_ID="73b74df77a22447e886ec46ebb975618";
	private final String Insta_URL_GET_Token = "https://instagram.com/oauth/authorize/?client_id=";
	public final String REDIRECT_URL="http://rateyourmusic.com/";
	String access_token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Log.d(MainActivity.TAG, "Start Intent Login");
		access_token="";
		AccessTokenURL = Insta_URL_GET_Token + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&response_type=token";
		WebView webview = (WebView) findViewById(R.id.webView1);
		webview.setWebViewClient(new HelloWeb());
		webview.loadUrl(AccessTokenURL);
		
    }
	
	private class HelloWeb extends WebViewClient 
	{
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) 
	    {
	    	view.loadUrl(url);
	    	if (url.startsWith(REDIRECT_URL)) {
	    		String urls[] = url.split("=");
				access_token=urls[1];
				Log.d(MainActivity.TAG, "Access_Token OK");
				Intent intent = new Intent();
				intent.putExtra("access_token", access_token);
				setResult(RESULT_OK,intent);
				finish();
			}
	        
	        return true;
	        	        
	    }
	    @Override
	    public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}
}

	
