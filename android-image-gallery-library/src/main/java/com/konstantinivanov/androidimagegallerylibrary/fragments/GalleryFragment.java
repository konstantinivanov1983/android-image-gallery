/**
 * GalleryFragment.java ---
 * <p/>
 * Copyright (C) 2014 Konstantin Ivanov
 * <p/>
 * Author: Konstantin Ivanov <ivanov@kula-tech.com>
 */
package com.konstantinivanov.androidimagegallerylibrary.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konstantinivanov.androidimagegallerylibrary.R;
import com.konstantinivanov.androidimagegallerylibrary.models.GalleryItem;
import com.konstantinivanov.androidimagegallerylibrary.models.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created:
 *
 * @author Konstantin Ivanov
 * @version 2
 * @since 13.08.2014.
 */
public class GalleryFragment extends android.support.v4.app.Fragment {

    public static final String ARGS_ITEM = "args.aig.fragment.item";
    public static final String EXTRA_ITEM = "extra.aig.fragment.item";

    private TouchImageView mImageViewTouch;
    private GalleryItem mGalleryItem;

    public static GalleryFragment newInstance(GalleryItem item) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle argument = new Bundle();
        argument.putParcelable(ARGS_ITEM, item);
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState == null) {
            if (getArguments() != null) {
                Parcelable parcelable = getArguments().getParcelable(ARGS_ITEM);
                if (parcelable != null && parcelable instanceof GalleryItem) {
                    mGalleryItem = (GalleryItem) parcelable;
                }
            }
        } else {
            Parcelable parcelable = savedInstanceState.getParcelable(EXTRA_ITEM);
            if (parcelable != null && parcelable instanceof GalleryItem) {
                mGalleryItem = (GalleryItem) parcelable;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(com.konstantinivanov.androidimagegallerylibrary.R.layout.aig_fragment_imageview, null);
        mImageViewTouch = (TouchImageView) view.findViewById(com.konstantinivanov.androidimagegallerylibrary.R.id.touch_image_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String thumb = mGalleryItem.getThumbUrl();
        String url = mGalleryItem.getImgUrl();

        if (isAdded()) {
            if (thumb != null && thumb.length() > 0) {
                Picasso.with(getActivity())
                        .load(thumb)
                        .into(mImageViewTouch, mCallback);
            } else {
                if (url != null && url.length() > 0) {
                    Picasso.with(getActivity())
                            .load(url)
                            .into(mImageViewTouch);
                } else {
                    Picasso.with(getActivity())
                            .load(getActivity().getResources().getString(R.string.maxim_url))
                            .into(mImageViewTouch);
                }
            }
        }
    }

    Callback mCallback = new Callback() {
        @Override
        public void onSuccess() {
            if (mGalleryItem != null) {
                String url = mGalleryItem.getImgUrl();
                if (isAdded() && url != null && url.length() > 0) {
                    Picasso.with(getActivity())
                            .load(mGalleryItem.getImgUrl())
                            .fit().centerInside()
                            .placeholder(mImageViewTouch.getDrawable())
                            .into(mImageViewTouch);
                }
            }
        }

        @Override
        public void onError() {
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_ITEM, mGalleryItem);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageViewTouch = null;
        mGalleryItem = null;
        mCallback = null;
    }

} // GalleryFragment
