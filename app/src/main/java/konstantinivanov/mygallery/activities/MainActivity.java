package konstantinivanov.mygallery.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import konstantinivanov.mygallery.authenticator.ImgurAuthinification;
import konstantinivanov.mygallery.R;
import konstantinivanov.mygallery.models.GalleryItem;


public class MainActivity extends Activity {

    final String TAG = "MyLogs";
    int mStartPosition = 0;             //Picture to start from
    GalleryItem[] mGalleryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Boolean isLoggedIn = ImgurAuthinification.getInstance().isLoggedIn();
        findViewById(R.id.button_view_gallery).setEnabled(isLoggedIn);
        findViewById(R.id.button_login).setEnabled(!isLoggedIn);
        findViewById(R.id.button_logout).setEnabled(isLoggedIn);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.button_logout:
                ImgurAuthinification.getInstance().logout();
                onStart();
                break;
            case R.id.button_view_gallery:
                Bundle bundle  = new Bundle();
                mGalleryItem = ImgurAuthinification.getInstance().returnGalleryItems();
                bundle.putParcelableArray("gallery items", mGalleryItem );
                bundle.putInt("start position", mStartPosition);
                Intent intentGallery = new Intent(this, ViewPagerActivity.class);
                intentGallery.putExtras(bundle);
                startActivity(intentGallery);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImgurAuthinification.getInstance().getNewAccessToken();
        if(ImgurAuthinification.getInstance().isLoggedIn()) {
            findViewById(R.id.button_view_gallery).setEnabled(true);
        }
    }

}
