package com.konstantinivanov.androidimagegallerylibrary.models;

import android.app.Activity;
import android.os.Parcelable;

/**
 * Created by Администратор on 13.08.2014.
 */
public class GetParcelableArray {

    GalleryItem[] mGalleryItems;
    Activity mActivity;

    public GetParcelableArray(Activity activity) {
        mActivity = activity;
    }

    public GalleryItem[] getArray(){
        try {
            Parcelable[] parcelableArray = mActivity.getIntent().getExtras()
                    .getParcelableArray("gallery items");
            mGalleryItems= new GalleryItem[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                mGalleryItems[i] = (GalleryItem) parcelableArray[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mGalleryItems;
    }

}
