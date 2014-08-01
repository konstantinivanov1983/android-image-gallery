package konstantinivanov.mygallery.activities;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import konstantinivanov.mygallery.authenticator.ImgurAuthinification;
import konstantinivanov.mygallery.R;
import konstantinivanov.mygallery.fragments.ViewFragment;
import konstantinivanov.mygallery.models.GalleryItem;

/**
 * Created by Konstantin on 04.07.2014.
 */
public class ViewPagerActivity extends FragmentActivity {

    public final static String TAG = "MyLogs";
    public static GalleryItem[] sGalleryItems;
    public static int sNumberOfPages;
    public static ViewPager mViewPager;
    int mPageSelected;
    TextView mTextViewUp;
    TextView mTextViewBottom;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ImgurAuthinification.getInstance().logout();
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        try {
            Parcelable[] parcelableArray =getIntent().getExtras().getParcelableArray("gallery items");
            sNumberOfPages = parcelableArray.length;
            Log.d(TAG, "Length of ParcelableArray : " + parcelableArray.length);
            sGalleryItems = new GalleryItem[parcelableArray.length];
            for (int i = 0; i<parcelableArray.length; i++) {
                sGalleryItems[i] = (GalleryItem) parcelableArray[i];
            }
        } catch (Exception e) {
            Log.d(TAG, " Error : " + e);
      }
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setBackgroundColor(Color.BLACK);
        final ImgurFragmentPagerAdapter pagerAdapter = new ImgurFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mPageSelected = getIntent().getExtras().getInt("start position");
        mViewPager.setCurrentItem(getIntent().getExtras().getInt("start position"));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPageSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                ViewFragment fragment = (ViewFragment) pagerAdapter
                        .instantiateItem(mViewPager, mPageSelected);
                mTextViewUp = (TextView) fragment.getView().findViewById(R.id.img_number);
                mTextViewBottom = (TextView) fragment.getView()
                        .findViewById(R.id.text_title);
                if (state == 0 ) {
                    if (sNumberOfPages>1) {
                        mTextViewUp.setVisibility(View.VISIBLE);
                        mTextViewUp.setText((mPageSelected + 1) + "/" + sNumberOfPages);
                    }
                    String title = sGalleryItems[mPageSelected].title;
                    if (title.length() > 0) {
                        mTextViewBottom.setVisibility(View.VISIBLE);
                        mTextViewBottom.setTextColor(Color.WHITE);
                        mTextViewBottom.setText(title);
                    }
                }
                else {
                    mTextViewUp.setVisibility(View.INVISIBLE);
                    mTextViewBottom.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    public static class ImgurFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public ImgurFragmentPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return sNumberOfPages;
        }

        @Override
        public Fragment getItem(int page) {
            return ViewFragment.newInstance(page);
        }
    }

}
