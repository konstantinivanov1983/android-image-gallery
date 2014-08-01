<<<<<<< HEAD
package konstantinivanov.mygallery.authenticator;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import konstantinivanov.mygallery.MyApplication;

/**
 * Created by Konstantin on 02.07.2014.
 */
public class ImgurRestClient {

    private static final String BASE_URL = "https://api.imgur.com/";
    private static AsyncHttpClient sClient = new AsyncHttpClient();

   public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.addHeader("Authorization", "Bearer " + MyApplication
                .getAppContext()
                .getSharedPreferences(ImgurAuthinification.SHARED_PREFS, 0)
                .getString("access_token", null));
       sClient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return  BASE_URL + relativeUrl;
    }
}
=======
package konstantinivanov.mygallery.authenticator;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import konstantinivanov.mygallery.MyApplication;

/**
 * Created by Konstantin on 02.07.2014.
 */
public class ImgurRestClient {

    private static final String BASE_URL = "https://api.imgur.com/";
    private static AsyncHttpClient sClient = new AsyncHttpClient();

   public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.addHeader("Authorization", "Bearer " + MyApplication
                .getAppContext()
                .getSharedPreferences(ImgurAuthinification.SHARED_PREFS, 0)
                .getString("access_token", null));
       sClient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return  BASE_URL + relativeUrl;
    }
}
>>>>>>> 1970ca9b1af3033d4f4009e26097a64323f4d52d
