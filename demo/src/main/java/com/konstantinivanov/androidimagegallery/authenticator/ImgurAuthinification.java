package com.konstantinivanov.androidimagegallery.authenticator;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.konstantinivanov.androidimagegallery.activities.LoginActivity;
import com.konstantinivanov.androidimagegallerylibrary.models.GalleryItem;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Администратор on 16.08.2014.
 */
public class ImgurAuthinification {


    static final String SHARED_PREFS = "imgur auth";
    static final String GET_URL = "https://api.imgur.com/3/gallery/r/pics/";
    static final String POST_URL = "https://api.imgur.com/oauth2/token";
    static final String TAG = "MyLogs";
    public GalleryItem[] mGalleryItems;
    public static Context mContext;

    public ImgurAuthinification(Context context) {
        mContext = context;
    }

    public boolean isLoggedIn () {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFS, 0);
        return !TextUtils.isEmpty(prefs.getString("refresh_token", null));
    }

    public void saveToken (String accessToken, String refreshToken, long expiresIn) {
        mContext.getSharedPreferences(SHARED_PREFS, 0)
                .edit()
                .putString("access_token", accessToken)
                .putString("refresh_token", refreshToken)
                .putLong("expires_in", expiresIn)
                .commit();
    }

    /**
     * Imgur Access Token have 1 hour time limit. getNewAccessToken return new
     * AccessToken each time MainActivity is start.
     */

    public void getNewAccessToken() {
        final SharedPreferences pref = mContext.getSharedPreferences(SHARED_PREFS,0);
        final String refreshToken = pref.getString("refresh_token",null);
        if (refreshToken == null) {
            Log.d(TAG, "refresh token is null, login first ");
        } else {
            pref.edit().remove("access_token").commit();
            RequestParams params = new RequestParams();
            params.put("refresh_token", refreshToken);
            params.put("client_id", LoginActivity.CLIENT_ID);
            params.put("client_secret", LoginActivity.CLIENT_SECRET);
            params.put("grant_type", "refresh_token");
            try {
                ImgurRestClient.post(POST_URL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            //response = response.getJSONObject("data");
                            String username = response.getString("account_username");
                            pref
                                    .edit()
                                    .putString("access_token", response.getString("access_token"))
                                    .putString("refresh_token", response.getString("refresh_token"))
                                    .commit();
                            getImages();
                        } catch (JSONException e) {
                            Log.d(TAG, "Error");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(TAG, " Error connot get new ACcESS TOKEN!!! ");
                    }
                });

            } catch (Exception e) {
                Log.d(TAG, "Error !!!!", e );
            }
        }
    }

    public void logout() {
        mContext.getSharedPreferences(SHARED_PREFS, 0)
                .edit()
                .clear()
                .commit();
    }

    public void getImages() {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFS,0);
        String accessToken = prefs.getString("access_token", null);
        RequestParams params = new RequestParams();
        params.put("access_token", accessToken);
        try {
            ImgurRestClient.get(GET_URL, null, mContext, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        mGalleryItems = new GalleryItem[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String string = jsonObject.getString("link");
                            String st1 = string.substring(0, string.lastIndexOf("."));
                            st1 = st1 + "t" + ".jpg";
                            mGalleryItems[i] = new GalleryItem(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("link"),
                                    st1,
                                    jsonObject.getString("title"));
                        }
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Error : " , e);
        }
    }

    public GalleryItem[] returnGalleryItems() {
        return mGalleryItems;
    }
}
