package com.konstantinivanov.androidimagegallery.authenticator;

import android.content.Context;
import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by Администратор on 16.08.2014.
 */
public class ImgurRestClient {

    private static AsyncHttpClient sAsyncClient = new AsyncHttpClient();
    private static AsyncHttpClient sSyncClient = new SyncHttpClient();


    public static void get(String url, RequestParams params, Context context, AsyncHttpResponseHandler responseHandler) {
        getsClient().addHeader("Authorization", "Bearer " + context
                .getSharedPreferences(ImgurAuthentication.SHARED_PREFS, 0)
                .getString("access_token", null));
        getsClient().get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        getsClient().post(url, params, responseHandler);
    }

    private static AsyncHttpClient getsClient () {
        // Return the synchronous HTTP client when the thread is not prepared
        if(Looper.myLooper() == null) {
            return sSyncClient;
        } else {
            return sAsyncClient;
        }
    }
}
