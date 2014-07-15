package konstantinivanov.mygallery.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import konstantinivanov.mygallery.models.ImageLoad;
import konstantinivanov.mygallery.MyApplication;
import konstantinivanov.mygallery.R;
import konstantinivanov.mygallery.activities.ViewPagerActivity;

/**
 * Created by Konstantin on 04.07.2014.
 */
public class ViewFragment extends Fragment {

    private static final int START_STANDALONE_PLAYER = 1;
    private static final int RESOLVE_SERVICE_MISSING = 2;
    private static final Pattern YoutubePattern = Pattern.compile("watch?v=([^&]*)");
    final static String TAG = "MyLogs";
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    ImageView mImageView;
    int mPageNumber;
    Drawable mDrawable;

    public static ViewFragment newInstance (int page) {
        ViewFragment fragment = new ViewFragment();
        Bundle argument = new Bundle();
        argument.putInt(ARGUMENT_PAGE_NUMBER, page);
        fragment.setArguments(argument);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    private com.squareup.picasso.Target target = new com.squareup.picasso.Target() {
        @Override
        public void onPrepareLoad(Drawable drawable) {
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            mDrawable = new BitmapDrawable(MyApplication.getAppContext().getResources(),bitmap);
            ImageLoad.getImage(mDrawable, ViewPagerActivity.sGalleryItems[mPageNumber].img_url,
                    mImageView);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            Log.d(TAG, " Error loading bitmap");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_view, null);
        mImageView = (ImageView) view.findViewById(R.id.image_view);
        mImageView.setBackgroundColor(getResources().getColor(android.R.color.black));
        TextView textView = (TextView) view.findViewById(R.id.image_title);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(getResources().getColor(R.color.background_color));
        textView.setText(ViewPagerActivity.sGalleryItems[mPageNumber].title);
        Log.d(TAG, "1");
        if (ViewPagerActivity.sGalleryItems[mPageNumber].img_url.contains("youtube")) {
            Log.d(TAG, "2");
            Matcher m;
            m = YoutubePattern.matcher(ViewPagerActivity.sGalleryItems[mPageNumber].img_url);
            m.find();
            String VIDEO_ID = m.group(1);
            Intent intentStartYoutube = YouTubeStandalonePlayer.createVideoIntent(
                    getActivity(),
                    getResources().getString(R.string.CLIENT_ID_YOUTUBE),
                    VIDEO_ID);
            if (intentStartYoutube!=null) {
                startActivityForResult(intentStartYoutube, START_STANDALONE_PLAYER);
            } else {
                YouTubeInitializationResult.SERVICE_MISSING
                        .getErrorDialog(getActivity(), RESOLVE_SERVICE_MISSING).show();
            }
        } else {
            Log.d(TAG, "3");
            Picasso.with(MyApplication.getAppContext())
                    .load(ViewPagerActivity.sGalleryItems[mPageNumber].thumb_img_url)
                    .into(target);
        }
        return view;
    }
}
