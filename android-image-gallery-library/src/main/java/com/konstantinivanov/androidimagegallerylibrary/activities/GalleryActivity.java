/**
 * GalleryActivity.java ---
 * <p/>
 * Copyright (C) 2014 Konstantin Ivanov
 * <p/>
 * Author: Konstantin Ivanov <ivanov@kula-tech.com>
 */
package com.konstantinivanov.androidimagegallerylibrary.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.konstantinivanov.androidimagegallerylibrary.R;
import com.konstantinivanov.androidimagegallerylibrary.fragments.GalleryFragment;
import com.konstantinivanov.androidimagegallerylibrary.models.GalleryItem;

import java.util.Arrays;


/**
 * Created:
 *
 * @author Konstantin Ivanov
 * @version 2
 * @since 13.08.2014.
 */
public class GalleryActivity extends FragmentActivity {

    final static String ARGS_POSITION = "args.aig.activity.position";
    final static String ARGS_ITEMS = "args.aig.activity.items";
    final static String EXTRA_POSITION = "extra.aig.activity.position";
    final static String EXTRA_ITEMS = "extra.aig.activity.items";

    private ViewPager mViewPager;
    private TextView mTextViewUp;
    private TextView mTextViewBottom;

    private GalleryItem[] mGalleryItems;
    private int mNumberOfPages;
    private int mPageSelected;


    public static void startActivity(Context context, String imgUrl) {
        startActivity(context, new GalleryItem[]{
                new GalleryItem(imgUrl)
        }, 0);
    }

    public static void startActivity(Context context, String imgUrls[], int position) {
        if (imgUrls == null)
            return;

        int length = imgUrls.length;

        if (length > 0 && position >= 0 && position < length) {
            GalleryItem gis[] = new GalleryItem[length];

            for (int i = 0; i < length; i++) {
                gis[i] = new GalleryItem(imgUrls[i]);
            }

            startActivity(context, gis, position);
        }
    }

    public static void startActivity(Context context, GalleryItem[] galleryItems, int startPosition) {
        Intent intent = new Intent(context, GalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(ARGS_ITEMS, galleryItems);
        bundle.putInt(ARGS_POSITION, startPosition);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aig_activity_gallerypager);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTextViewUp = (TextView) findViewById(R.id.img_number);
        mTextViewBottom = (TextView) findViewById(R.id.img_title);

        if (savedInstanceState == null) {
            try {
                Bundle bundle = getIntent().getExtras();
                Parcelable[] parcelables = bundle.getParcelableArray(ARGS_ITEMS);
                mGalleryItems = null;
                if (parcelables != null) {
                    mGalleryItems
                            = Arrays.copyOf(parcelables, parcelables.length, GalleryItem[].class);
                }
                mPageSelected = bundle.getInt(ARGS_POSITION);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Parcelable[] parcelable = savedInstanceState.getParcelableArray(EXTRA_ITEMS);
            if (parcelable != null && parcelable instanceof GalleryItem[]) {
                mGalleryItems = (GalleryItem[]) parcelable;
                mPageSelected = savedInstanceState.getInt(EXTRA_POSITION);
            }
        }

        if (mGalleryItems != null && mGalleryItems.length > 0) {
            mNumberOfPages = mGalleryItems.length;
            if (mPageSelected > mNumberOfPages) {
                mPageSelected = 0;
            }

            ImgurFragmentPagerAdapter pagerAdapter
                    = new ImgurFragmentPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(pagerAdapter);
            mViewPager.setCurrentItem(mPageSelected);
            setTextToImage(mPageSelected);
        } else {
            Toast.makeText(GalleryActivity.this, getResources().getString(R.string.alert),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewPager.setOnPageChangeListener(mViewPagerOnTouchListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray(EXTRA_ITEMS, mGalleryItems);
        outState.putInt(EXTRA_POSITION, mPageSelected);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mViewPager = null;
        mTextViewUp = null;
        mTextViewBottom = null;
        mGalleryItems = null;
        mViewPagerOnTouchListener = null;
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void setTextToImage(int position) {
        if (mGalleryItems != null) {
            GalleryItem item = mGalleryItems[position];
            if (item != null) {
                String title = item.getTitle();
                if (mNumberOfPages > 1) {
                    mTextViewUp.setText((position + 1) + " / " + mNumberOfPages);
                } else {
                    mTextViewUp.setText(" ");
                }
                if (title != null && title.length() > 0) {
                    mTextViewBottom.setText(title);
                } else {
                    mTextViewBottom.setText(" ");
                }
            }
        }
    }

    ViewPager.OnPageChangeListener mViewPagerOnTouchListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            setTextToImage(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    public class ImgurFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public ImgurFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mGalleryItems.length;
        }

        @Override
        public Fragment getItem(int page) {
            return GalleryFragment.newInstance(mGalleryItems[page]);
        }
    }

} // GalleryActivity
