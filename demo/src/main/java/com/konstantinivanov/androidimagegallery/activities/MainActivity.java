package com.konstantinivanov.androidimagegallery.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.konstantinivanov.androidimagegallery.authenticator.ImgurAuthinification;
import com.konstantinivanov.androidimagegallery.R;
import com.konstantinivanov.androidimagegallerylibrary.activities.GalleryPagerActivity;
import com.konstantinivanov.androidimagegallerylibrary.models.GalleryItem;


/**
 * Created by Администратор on 16.08.2014.
 */
public class MainActivity extends Activity{

    final String GALLERY_ITEMS = "gallery items";
    final String POSITION = "start position";
    public static int sStartPosition = 0;             //Picture to start from
    GalleryItem[] mGalleryItem;
    Button mButtonViewGallery;
    Boolean mIsLoggedIn;
    ImgurAuthinification mImgurAuthinification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgurAuthinification = new ImgurAuthinification(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsLoggedIn = mImgurAuthinification.isLoggedIn();
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
                mImgurAuthinification.logout();
                findViewById(R.id.button_view_gallery).setEnabled(false);
                findViewById(R.id.button_logout).setEnabled(false);
                findViewById(R.id.button_login).setEnabled(true);
                break;
            case R.id.button_view_gallery:
                Bundle bundle  = new Bundle();
                mGalleryItem = mImgurAuthinification.returnGalleryItems();
                bundle.putParcelableArray(GALLERY_ITEMS, mGalleryItem );
                bundle.putInt(POSITION, sStartPosition);
                GalleryPagerActivity.startActivity(this, bundle);
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
            mImgurAuthinification.getNewAccessToken();
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
