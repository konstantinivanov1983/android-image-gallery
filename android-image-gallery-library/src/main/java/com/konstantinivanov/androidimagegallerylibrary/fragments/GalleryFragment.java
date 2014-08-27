package com.konstantinivanov.androidimagegallerylibrary.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konstantinivanov.androidimagegallerylibrary.R;
import com.konstantinivanov.androidimagegallerylibrary.activities.GalleryPagerActivity;
import com.konstantinivanov.androidimagegallerylibrary.models.GalleryItem;
import com.konstantinivanov.androidimagegallerylibrary.models.GetParcelableArray;
import com.konstantinivanov.androidimagegallerylibrary.models.ImageLoad;
import com.konstantinivanov.androidimagegallerylibrary.models.TouchImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Администратор on 13.08.2014.
 */
public class GalleryFragment extends android.support.v4.app.Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    TouchImageView mImageViewTouch;
    int mPageNumber;
    Drawable mDrawable;
    GalleryItem[] mGalleryItems;

    public static GalleryFragment newInstance (int page) {
        GalleryFragment fragment = new GalleryFragment();
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
        try {
            mGalleryItems = new GetParcelableArray(getActivity()).getArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private com.squareup.picasso.Target mTarget = new com.squareup.picasso.Target() {

        @Override
        public void onPrepareLoad(Drawable drawable) {
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            mDrawable = new BitmapDrawable(getActivity().getResources(),bitmap);
            ImageLoad.getImage(mDrawable, mGalleryItems[mPageNumber].imgUrl,
                    mImageViewTouch);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            Log.d(GalleryPagerActivity.TAG, " Error loading bitmap");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.aig_fragment_imageview, null);
        mImageViewTouch = (TouchImageView) view.findViewById(R.id.touch_image_view);
        mImageViewTouch.setBackgroundColor(getResources().getColor(android.R.color.black));
        if ( mGalleryItems[mPageNumber].thumbImgUrl != null &&
                mGalleryItems[mPageNumber].imgUrl != null) {
            Picasso.with(getActivity())
                    .load(mGalleryItems[mPageNumber].thumbImgUrl)
                    .into(mTarget);
        }
        else if (mGalleryItems[mPageNumber].thumbImgUrl == null ) {
            ImageLoad.getImage(null, mGalleryItems[mPageNumber].imgUrl, mImageViewTouch);
        }
        else {
            ImageLoad.getImage(null, mGalleryItems[mPageNumber].thumbImgUrl, mImageViewTouch);
        }
        return view;
    }
}
