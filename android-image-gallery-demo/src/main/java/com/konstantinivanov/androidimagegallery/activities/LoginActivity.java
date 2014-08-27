package com.konstantinivanov.androidimagegallery.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.konstantinivanov.androidimagegallery.R;
import com.konstantinivanov.androidimagegallery.authenticator.ImgurAuthentication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Администратор on 16.08.2014.
 */
public class LoginActivity extends Activity {

    private static final Pattern accessTokenPattern = Pattern.compile("access_token=([^&]*)");
    private static final Pattern refreshTokenPattern = Pattern.compile("refresh_token=([^&]*)");
    private static final Pattern expiresInPattern = Pattern.compile("expires_in=(\\d+)");
    public static final String CLIENT_ID = "ce3bf90e9178ce5";
    public static final String CLIENT_SECRET = "9e9eb8d22b6a1672fe66037b2b9baa34b97e6704";
    private final String GET_EXTRA_ACCESS_TOKEN_URL =
            "https://api.imgur.com/oauth2/authorize?client_id=";
    public final String REDIRECT_URL = "https://rateyourmusic.com/";
    private WebView mWebView;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgurlogin);
        mActivity = this;
        mWebView = (WebView) findViewById(R.id.web_view);
        startWebView();
        mWebView.loadUrl(GET_EXTRA_ACCESS_TOKEN_URL + CLIENT_ID + "&response_type=token");
    }

    private void startWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                boolean token = false;
                if (url.startsWith(REDIRECT_URL)) {
                    token = true;
                    Matcher m;
                    m = refreshTokenPattern.matcher(url);
                    m.find();
                    String refreshToken = m.group(1);
                    m = accessTokenPattern.matcher(url);
                    m.find();
                    String accessToken = m.group(1);
                    m = expiresInPattern.matcher(url);
                    m.find();
                    long expiresIn = Long.valueOf(m.group(1));
                    (new ImgurAuthentication(mActivity)).saveToken(accessToken, refreshToken, expiresIn);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                }
                return token;
            }
        });
    }
}
