/**
 * GalleryItem.java ---
 * <p/>
 * Copyright (C) 2014 Konstantin Ivanov
 * <p/>
 * Author: Konstantin Ivanov <ivanov@kula-tech.com>
 */
package com.konstantinivanov.androidimagegallerylibrary.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created:
 *
 * @author Konstantin Ivanov
 * @version 2
 * @since 13.08.2014.
 */
public class GalleryItem implements Parcelable {

    String mImgUrl;
    String mThumbUrl;
    String mImgTitle;

    public GalleryItem(String imgUrl) {
        this(imgUrl, null);
    }

    public GalleryItem(String imgUrl, String thumbImgUrl) {
        this(imgUrl, thumbImgUrl, null);
    }

    public GalleryItem(String imgUrl, String thumbImgUrl, String imgTitle) {
        mImgUrl = imgUrl;
        mThumbUrl = thumbImgUrl;
        mImgTitle = imgTitle;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public String getTitle() {
        return mImgTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mImgUrl);
        parcel.writeString(mThumbUrl);
        parcel.writeString(mImgTitle);
    }

    public static final Creator<GalleryItem> CREATOR = new Creator<GalleryItem>() {

        @Override
        public GalleryItem createFromParcel(Parcel in) {
            return new GalleryItem(in);
        }

        @Override
        public GalleryItem[] newArray(int size) {
            return new GalleryItem[size];
        }
    };

    private GalleryItem(Parcel parcel) {
        mImgUrl = parcel.readString();
        mThumbUrl = parcel.readString();
        mImgTitle = parcel.readString();
    }

} // GalleryItem
