package com.konstantinivanov.slideviewdemo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.konstantinivanov.slideviewdemo.DataAsyncTask;
import com.konstantinivanov.slideviewdemo.R;

public class LoginActivity extends Activity {

	private final String CLIENT_ID="73b74df77a22447e886ec46ebb975618";
	private final String GET_EXTRA_ACCESS_TOKEN_URL =
            "https://instagram.com/oauth/authorize/?client_id=";
    private final String USERS_FEED_URL =
            "https://api.instagram.com/v1/users/self/feed?access_token=";
	public final String REDIRECT_URL="http://rateyourmusic.com/";
    private String mAccessToken = "";
    private String mAccessTokenURL;
    Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		Log.d(MainActivity.TAG, "Start Intent LoginActivity");
		mAccessTokenURL = GET_EXTRA_ACCESS_TOKEN_URL + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URL + "&response_type=token";
		WebView webview = (WebView) findViewById(R.id.webView);
		webview.setWebViewClient(new LoginWebViewClient());
		webview.loadUrl(mAccessTokenURL);
    }
	
	private class LoginWebViewClient extends WebViewClient
	{
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url)
	    {
	    	view.loadUrl(url);
	    	if (url.startsWith(REDIRECT_URL)) {
	    		String urls[] = url.split("=");
                if (urls==null || urls.length==0) {
                    Log.d(MainActivity.TAG,"Access token error");
                }
                else {
                    mAccessToken=urls[1];
                    SharedPreferences sh = mContext.getSharedPreferences("AccessToken",MODE_PRIVATE);
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString(MainActivity.TOKEN, mAccessToken);
                    ed.commit();
                    String  stringUrl = USERS_FEED_URL + mAccessToken;
                    new DataAsyncTask(mContext).execute(stringUrl);
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
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

	
