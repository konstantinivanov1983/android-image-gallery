package com.konstantinivanov.slideviewdemo;

//Фрагмент отвечающий за загрузку и отображение фотографии
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyFragment extends Fragment  {
	int PageNumber;
	int backColor;
	static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
	Cursor cursor;
	Context mContext;
	
	
	static MyFragment newInstance (int page) {
		MyFragment fragment1 = new MyFragment();
		Bundle arguments = new Bundle();
		arguments.putInt(ARGUMENT_PAGE_NUMBER,page);
		fragment1.setArguments(arguments);
		return fragment1;
	}
	
	@Override
	public void onCreate(Bundle savedinstancestate) {
		super.onCreate(savedinstancestate);
		PageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
		Random rnd = new Random();
	    backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment1, null);
		ImageView im1 = (ImageView) view.findViewById(R.id.imageView1);
		MyDB db;
		db = new MyDB(MyTask.getContext());
		db.open();
		cursor = db.getAllData();
		cursor.moveToPosition(PageNumber);
		//Получаем адрес фото из БД
		String urlImage = cursor.getString(cursor.getColumnIndex(MyDB.Column_URL));
		db.close();
		URL myURL;
		try {
			myURL= new URL(urlImage);
			new MyAsyncTask(im1).execute(myURL);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return view;
	}
	
	private class MyAsyncTask extends AsyncTask<URL, Void, Bitmap> {
		
		ImageView w;
		
		public MyAsyncTask(ImageView i1) {
			w = i1;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}
		
		@Override
		protected Bitmap doInBackground(URL... urls) {
			Bitmap image = null;
			URL url = urls[0];
			try { 
				image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return image;
		}
		
		protected void onPostExecute(Bitmap result) {
			w.setImageBitmap(result);
		}
		
		
	}

}

	
