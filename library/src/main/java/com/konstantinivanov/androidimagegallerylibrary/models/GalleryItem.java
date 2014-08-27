package com.konstantinivanov.androidimagegallerylibrary.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Администратор on 13.08.2014.
 */
public class GalleryItem implements Parcelable {

    public String imgUrl;
    public String thumbImgUrl;
    public String imgTitle;

    public GalleryItem (String _url, String _thumb_img, String _title) {
        imgUrl = _url;
        thumbImgUrl = _thumb_img;
        imgTitle = _title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imgUrl);
        parcel.writeString(thumbImgUrl);
        parcel.writeString(imgTitle);
    }

    public static final Creator<GalleryItem> CREATOR = new Creator<GalleryItem>() {

        @Override
        public GalleryItem createFromParcel (Parcel in) {
            return new GalleryItem(in);
        }

        @Override
        public GalleryItem[] newArray(int size) {
            return new GalleryItem[size];
        }
    };

    private GalleryItem(Parcel parcel){
        imgUrl = parcel.readString();
        thumbImgUrl = parcel.readString();
        imgTitle = parcel.readString();
    }

}
