package com.konstantinivanov.androidimagegallery.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.konstantinivanov.androidimagegallery.authenticator.ImgurAuthentication;
import com.konstantinivanov.androidimagegallery.R;
import com.konstantinivanov.androidimagegallerylibrary.activities.GalleryActivity;


/**
 * Created by Администратор on 16.08.2014.
 */
public class MainActivity extends Activity{

    int mStartPosition = 0;             //Picture to start from
    Button mButtonViewGallery;
    Boolean mIsLoggedIn;
    ImgurAuthentication mImgurAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgurAuthentication = new ImgurAuthentication(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsLoggedIn = mImgurAuthentication.isLoggedIn();
        findViewById(R.id.button_view_gallery).setEnabled(false);
        findViewById(R.id.button_login).setEnabled(!mIsLoggedIn);
        findViewById(R.id.button_logout).setEnabled(mIsLoggedIn);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.button_logout:
                mImgurAuthentication.logout();
                findViewById(R.id.button_view_gallery).setEnabled(false);
                findViewById(R.id.button_logout).setEnabled(false);
                findViewById(R.id.button_login).setEnabled(true);
                break;
            case R.id.button_view_gallery:
                GalleryActivity.startActivity(this,
                        mImgurAuthentication.getGalleryItems(), mStartPosition);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mButtonViewGallery = (Button)findViewById(R.id.button_view_gallery);
        new GalleryImagesAsyncTask().execute();
    }

    private class GalleryImagesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... contexts) {
            mImgurAuthentication.getNewAccessToken();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mIsLoggedIn)
                mButtonViewGallery.setEnabled(true);
        }
    }
}
