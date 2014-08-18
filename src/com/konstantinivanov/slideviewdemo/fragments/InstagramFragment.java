package com.konstantinivanov.slideviewdemo.fragments;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.konstantinivanov.slideviewdemo.R;
import com.konstantinivanov.slideviewdemo.activities.MainActivity;
import com.konstantinivanov.slideviewdemo.data.MyDataBase;

public class InstagramFragment extends Fragment  {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    ImageView mImageView;
	int mPageNumber;
	Cursor mCursor;

	public static InstagramFragment newInstance (int page) {
		InstagramFragment fragment = new InstagramFragment();
		Bundle argument = new Bundle();
		argument.putInt(ARGUMENT_PAGE_NUMBER, page);
		fragment.setArguments(argument);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedinstancestate) {
		super.onCreate(savedinstancestate);
		mPageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
		mImageView = (ImageView) view.findViewById(R.id.fragment);
		MyDataBase db;
        db = new MyDataBase(getActivity());
        try {
            db.open();
        } catch (Exception e) {
            Log.d(MainActivity.TAG,"Error DataBase open");
            e.printStackTrace();
        }
		mCursor = db.getAllData();
		mCursor.moveToPosition(mPageNumber);
		String urlImage = mCursor.getString(mCursor.getColumnIndex(MyDataBase.COLUMN_URL));
		db.close();
		new MyAsyncTask().execute(urlImage);
		return view;
	}
	
	private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected Bitmap doInBackground(String... urls) {
            String url = "";
            if (urls.length > 0) {
                url = urls[0];
            }
            InputStream input = null;
            try {
                URL urlConnection = new URL(url);
                input = urlConnection.openStream();
            }
            catch (MalformedURLException e) {
                Log.d(MainActivity.TAG, " Error URL");
                e.printStackTrace();
            }
            catch (IOException e) {
                Log.d(MainActivity.TAG," Error input stream");
                e.printStackTrace();
            }
            return BitmapFactory.decodeStream(input);
		}
		
		protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            mImageView.setImageBitmap(result);
		}
		
		
	}

}

	
