package com.konstantinivanov.slideviewdemo;

//Используя AsynkTask елаем запрос сайту, получаем ответ в виде формата JSON
//из JSON извлекаем адреса фотографий и добавляем в базу данных SQLite


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class MyTask extends AsyncTask<String, Void, String> {
	
	private static Context mContext;
	
	MyDB db;
	
	public MyTask(Context context) {
		mContext = context;
	}
	
	public static Context getContext() {
		return mContext;
	}
	
	@Override
	protected String doInBackground(String... instaURL) {
		StringBuilder mStringBuilder = new StringBuilder();
		
		for (String searchURL : instaURL) {
			HttpClient mClient = new DefaultHttpClient();
			try {
				HttpGet mGet = new HttpGet(searchURL);
				HttpResponse mResponse = mClient.execute(mGet);
				StatusLine searchStatus = mResponse.getStatusLine();
				if (searchStatus.getStatusCode() == 200) {
		
					HttpEntity mEntity = mResponse.getEntity();
					InputStream mContent = mEntity.getContent();

					InputStreamReader mInput = new InputStreamReader(
							mContent);
					BufferedReader mReader = new BufferedReader(
							mInput);
					String lineIn;
					while ((lineIn = mReader.readLine()) != null) {
						mStringBuilder.append(lineIn);
					}
				} else
					Log.d(MainActivity.TAG, "Error");
			} catch (Exception e) {
				Log.d(MainActivity.TAG, "Error");
				e.printStackTrace();
			}
		}
		return mStringBuilder.toString();
		
	}

	protected void onPostExecute(String result) {
		
		try {
			JSONObject jsonObject = (JSONObject) new JSONTokener(result).nextValue();
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			db = new MyDB(mContext);
			db.open();
			for (int i = 0; i<jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("low_resolution");
				String imageUrlString = json.getString("url");
				db.AddRec("MyID", imageUrlString);
			}
			db.close();
	
		} catch (Exception e) {
			Log.d(MainActivity.TAG, "Error 2 ");
			e.printStackTrace();
		}
	}
}



