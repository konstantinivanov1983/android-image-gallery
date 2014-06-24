
package com.konstantinivanov.slideviewdemo.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.konstantinivanov.slideviewdemo.R;

public class MainActivity extends Activity {
	
	public static final String TAG = "MyLog";
    public static final String TOKEN = "token";
	Button mNextButton;
	int mRequestCode = 1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNextButton = (Button)findViewById(R.id.buttonView);
        SharedPreferences sh = getSharedPreferences("AccessToken",MODE_PRIVATE);
        if (!(sh.getString(TOKEN,"").length() > 0 )) {
            mNextButton.setEnabled(false);
        }
	}
	
	public void OnClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.buttonLogin :
			intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, mRequestCode);
			break;
		case R.id.buttonView :
			intent = new Intent(this, InstagramImageActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			finishActivity(requestCode);
			mNextButton.setEnabled(true);
			}
	    else {
	      Log.d(TAG, "error 404");
	    }
	}
}
