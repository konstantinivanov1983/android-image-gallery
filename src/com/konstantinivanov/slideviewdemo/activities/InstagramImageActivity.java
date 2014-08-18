package com.konstantinivanov.slideviewdemo.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.konstantinivanov.slideviewdemo.R;
import com.konstantinivanov.slideviewdemo.fragments.InstagramFragment;

public class InstagramImageActivity extends FragmentActivity {

	static final int NUM_PAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        InstagramFragmentPagerAdapter pagerAdapter =
                new InstagramFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }
        
        public static class InstagramFragmentPagerAdapter extends FragmentStatePagerAdapter {
			
        	public InstagramFragmentPagerAdapter(FragmentManager fm) {
        		super(fm);
        	}

			@Override
			public int getCount() {
				return NUM_PAGE;
			}
			
			@Override
			public android.support.v4.app.Fragment getItem(int page) {
                return InstagramFragment.newInstance(page);
			}
		}
}
