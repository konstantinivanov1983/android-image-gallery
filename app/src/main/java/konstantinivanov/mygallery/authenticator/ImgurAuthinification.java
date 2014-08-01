<<<<<<< HEAD
package konstantinivanov.mygallery.authenticator;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import konstantinivanov.mygallery.MyApplication;
import konstantinivanov.mygallery.activities.LoginActivity;
import konstantinivanov.mygallery.models.GalleryItem;

/**
 * Created by Konstantin on 02.07.2014.
 */
public class ImgurAuthinification {


    static final String SHARED_PREFS = " imgur auth";
    private final String TAG = "MyLogs";
    public GalleryItem[] mGalleryItems;
    private ImgurAuthinification() {}
    private static ImgurAuthinification sInstance;


    public static ImgurAuthinification getInstance () {
        if (sInstance == null) {
            sInstance = new ImgurAuthinification();
        }
        return sInstance;
    }



    public boolean isLoggedIn () {
        Context context = MyApplication.getAppContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, 0);
        return !TextUtils.isEmpty(prefs.getString("refresh_token", null));
    }

    public void saveToken (String accessToken, String refreshToken, long expiresIn) {
        Context context = MyApplication.getAppContext();
        context.getSharedPreferences(SHARED_PREFS, 0)
                .edit()
                .putString("access_token", accessToken)
                .putString("refresh_token", refreshToken)
                .putLong("expires_in", expiresIn)
                .commit();
    }

    public void getNewAccessToken(final View view) {
        Context context = MyApplication.getAppContext();
        final SharedPreferences pref = context.getSharedPreferences(SHARED_PREFS,0);
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
                ImgurRestClient.post("oauth2/token", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            //response = response.getJSONObject("data");
                            String username = response.getString("account_username");
                            Log.d(TAG, "Access Token " + response.getString("access_token"));
                            pref
                                    .edit()
                                    .putString("access_token", response.getString("access_token"))
                                    .putString("refresh_token", response.getString("refresh_token"))
                                    .commit();
                            getImages(view);
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
            Log.d(TAG, "New access token: " + pref.getString("access_token", null));
        }
    }

    public void logout() {
        Context context = MyApplication.getAppContext();
        context.getSharedPreferences(SHARED_PREFS, 0)
                .edit()
                .clear()
                .commit();


    }

    public void getImages(final View view) {
        SharedPreferences prefs = MyApplication.getAppContext().getSharedPreferences(SHARED_PREFS,0);
        String accessToken = prefs.getString("access_token", null);
        RequestParams params = new RequestParams();
        params.put("access_token", accessToken);
        try {
            ImgurRestClient.get("3/gallery/r/pics/", null , new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        mGalleryItems = new GalleryItem[jsonArray.length()];
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String string = jsonObject.getString("link");
                            String st1 = string.substring(0,string.lastIndexOf("."));
                            st1 = st1 + "t" + ".jpg";
                            mGalleryItems[i] = new GalleryItem(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("link"),
                                    st1,
                                    jsonObject.getString("title"));
                        }
                        view.setEnabled(true);

                    } catch (Exception e) {
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
=======
package konstantinivanov.mygallery.authenticator;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import konstantinivanov.mygallery.MyApplication;
import konstantinivanov.mygallery.activities.LoginActivity;
import konstantinivanov.mygallery.models.GalleryItem;

/**
 * Created by Konstantin on 02.07.2014.
 */
public class ImgurAuthinification {


    static final String SHARED_PREFS = " imgur auth";
    private final String TAG = "MyLogs";
    public GalleryItem[] mGalleryItems;
    private ImgurAuthinification() {}
    private static ImgurAuthinification sInstance;


    public static ImgurAuthinification getInstance () {
        if (sInstance == null) {
            sInstance = new ImgurAuthinification();
        }
        return sInstance;
    }

    public boolean isLoggedIn () {
        Context context = MyApplication.getAppContext();
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, 0);
        return !TextUtils.isEmpty(prefs.getString("refresh_token", null));
    }

    public void saveToken (String accessToken, String refreshToken, long expiresIn) {
        Context context = MyApplication.getAppContext();
        context.getSharedPreferences(SHARED_PREFS, 0)
                .edit()
                .putString("access_token", accessToken)
                .putString("refresh_token", refreshToken)
                .putLong("expires_in", expiresIn)
                .commit();
    }

    public void getNewAccessToken() {
        Context context = MyApplication.getAppContext();
        final SharedPreferences pref = context.getSharedPreferences(SHARED_PREFS,0);
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
                ImgurRestClient.post("oauth2/token", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            //response = response.getJSONObject("data");
                            String username = response.getString("account_username");
                            Log.d(TAG, "Access Token " + response.getString("access_token"));
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
            Log.d(TAG, "New access token: " + pref.getString("access_token", null));
        }
    }

    public void logout() {
        Context context = MyApplication.getAppContext();
        context.getSharedPreferences(SHARED_PREFS, 0)
                .edit()
                .clear()
                .commit();
    }

    public void getImages() {
        SharedPreferences prefs = MyApplication.getAppContext().getSharedPreferences(SHARED_PREFS,0);
        String accessToken = prefs.getString("access_token", null);
        RequestParams params = new RequestParams();
        params.put("access_token", accessToken);
        try {
            ImgurRestClient.get("3/gallery/r/pics/", null , new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        mGalleryItems = new GalleryItem[jsonArray.length()];
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String string = jsonObject.getString("link");
                            String st1 = string.substring(0,string.lastIndexOf("."));
                            st1 = st1 + "t" + ".jpg";
                            mGalleryItems[i] = new GalleryItem(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("link"),
                                    st1,
                                    jsonObject.getString("title"));
                        }
                    } catch (Exception e) {
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
>>>>>>> 1970ca9b1af3033d4f4009e26097a64323f4d52d
