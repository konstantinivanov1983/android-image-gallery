package konstantinivanov.mygallery.activities;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
        Parcelable[] parcelableArray =getIntent().getExtras().getParcelableArray("gallery items");
        sNumberOfPages = parcelableArray.length;
        sGalleryItems = new GalleryItem[parcelableArray.length];
        for (int i = 0; i<parcelableArray.length; i++) {
            sGalleryItems[i] = (GalleryItem) parcelableArray[i];
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerTabStrip pagerTabStripUp = (PagerTabStrip) findViewById(R.id.pager_tab_strip_up);
        pagerTabStripUp.setTextColor(getResources().getColor(android.R.color.white));
        pagerTabStripUp.setBackgroundColor(getResources().getColor(R.color.background_color));
        ImgurFragmentPagerAdapter pagerAdapter = new ImgurFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(getIntent().getExtras().getInt("start position"));
    }

    public static class ImgurFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public ImgurFragmentPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position +1)  + " / " + sNumberOfPages;
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
