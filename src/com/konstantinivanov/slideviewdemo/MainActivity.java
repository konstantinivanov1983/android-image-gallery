package com.konstantinivanov.slideviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	static final String TAG = "Log";
	Button mButton1;
	Button mButton2;
	int mRequestCode = 1;
	String access_token;
	private final String Insta_URL_GET_URLS = "https://api.instagram.com/v1/users/self/feed?access_token=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mButton2 = (Button)findViewById(R.id.button2);
		mButton2.setEnabled(false);
	}
	
	public void OnClick(View v) {
		
		Intent intent;
		switch (v.getId()) {
		case R.id.button1 :
			//Через webview авторизируемся в Instagram
			intent = new Intent(this, Login.class);
			startActivityForResult(intent, mRequestCode);
			break;
		case R.id.button2 :
			//Используя viewpager отображаем фотографии 
			intent = new Intent(this,InstagramImage.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode == RESULT_OK) {
			access_token = data.getStringExtra("access_token");
			String mString = Insta_URL_GET_URLS + access_token;
			//Получили валидный access_token от сайта, делаем запрос на адреса фото
			new MyTask(this).execute(mString);
			finishActivity(requestCode);
			mButton2.setEnabled(true);
			}
	    else {
	      Log.d(TAG, "error 404");
	    }
	}
}
