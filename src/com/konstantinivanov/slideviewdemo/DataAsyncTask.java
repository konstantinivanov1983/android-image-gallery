package com.konstantinivanov.slideviewdemo;

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

import com.konstantinivanov.slideviewdemo.activities.MainActivity;
import com.konstantinivanov.slideviewdemo.data.MyDataBase;

public class DataAsyncTask extends AsyncTask<String, Void, String> {
	
	private static Context mContext;
	MyDataBase db;
	
	public DataAsyncTask(Context context) {
		mContext = context;
	}

	@Override
	protected String doInBackground(String... instagramUrl) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String searchURL : instagramUrl) {
			HttpClient client = new DefaultHttpClient();
			try {
				HttpGet httpGet = new HttpGet(searchURL);
				HttpResponse response = client.execute(httpGet);
				StatusLine searchStatus = response.getStatusLine();
				if (searchStatus.getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					InputStreamReader input = new InputStreamReader(
							content);
					BufferedReader reader = new BufferedReader(
                            input);
					String lineIn;
					while ((lineIn = reader.readLine()) != null) {
						stringBuilder.append(lineIn);
					}
				} else
					Log.d(MainActivity.TAG, "Error");
			} catch (Exception e) {
				Log.d(MainActivity.TAG, "Error");
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}

	protected void onPostExecute(String result) {
		
		try {
			JSONObject jsonObject = (JSONObject) new JSONTokener(result).nextValue();
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			db = new MyDataBase(mContext);
            try {
                db.open();
            } catch (Exception e) {
                Log.d(MainActivity.TAG,"Error DataBase open");
                e.printStackTrace();
            }
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



