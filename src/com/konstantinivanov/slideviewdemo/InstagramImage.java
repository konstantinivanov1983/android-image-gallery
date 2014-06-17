package com.konstantinivanov.slideviewdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class InstagramImage extends FragmentActivity {

	static final int Num_page = 20; 
	
	ViewPager viewpager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview1);
        viewpager = (ViewPager) findViewById(R.id.pager);
        MyFragmentPagerAdapter pageradapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(pageradapter);
        viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				Log.d(MainActivity.TAG, "onPageSelected, position = " + arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
        
       });
    }
        
        public static class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
			
        	public MyFragmentPagerAdapter(FragmentManager fm) {
        		super(fm);
        	}
        	
			@Override
			public int getCount() {
				return Num_page;
			}
			
			@Override
			public android.support.v4.app.Fragment getItem(int page) {
				return MyFragment.newInstance(page);
			}
		}
}
