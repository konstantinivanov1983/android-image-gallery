package com.konstantinivanov.androidimagegallerylibrary.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.konstantinivanov.androidimagegallerylibrary.R;
import com.konstantinivanov.androidimagegallerylibrary.fragments.GalleryFragment;
import com.konstantinivanov.androidimagegallerylibrary.models.GalleryItem;
import com.konstantinivanov.androidimagegallerylibrary.models.GetParcelableArray;


/**
 * Created by Администратор on 13.08.2014.
 */
public class GalleryPagerActivity extends FragmentActivity {

    public final static String TAG = "MyLogs";
    GalleryItem[] mGalleryItems;
    int mNumberOfPages;
    ViewPager mViewPager;
    int mPageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        try {
            mGalleryItems = new GetParcelableArray(this).getArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mNumberOfPages = mGalleryItems.length;
        mViewPager = (ViewPager) findViewById(com.konstantinivanov.androidimagegallerylibrary.R.id.viewpager);
        mViewPager.setBackgroundColor(Color.BLACK);
        final ImgurFragmentPagerAdapter pagerAdapter = new ImgurFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mPageSelected = getIntent().getExtras().getInt("start position");
        if (mPageSelected > mNumberOfPages) {
            mPageSelected = 0;
        }
        setTextToImage(mPageSelected);
        mViewPager.setCurrentItem(mPageSelected);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTextToImage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public static void startActivity(Context context, Bundle bundle)
    {
        Intent intent = new Intent(context, GalleryPagerActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void setTextToImage(int position) {
        TextView textViewUp = (TextView) findViewById(com.konstantinivanov.androidimagegallerylibrary.R.id.img_number);
        TextView textViewBottom = (TextView) findViewById(com.konstantinivanov.androidimagegallerylibrary.R.id.img_title);
        String title = mGalleryItems[position].imgTitle;
        if (mNumberOfPages > 1) {
            textViewUp.setText((position + 1) + " / " + mNumberOfPages);
        } else {
            textViewUp.setText(" ");
        }
        if (title.length() > 0) {
            textViewBottom.setText(title);
        } else {
            textViewBottom.setText(" ");
        }
    }

    public class ImgurFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public ImgurFragmentPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mNumberOfPages;
        }

        @Override
        public Fragment getItem(int page) {
            return GalleryFragment.newInstance(page);
        }
    }

}
