package com.konstantinivanov.androidimagegallerylibrary.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Администратор on 13.08.2014.
 */
public class GalleryItem implements Parcelable {

    public String id;
    public String imgUrl;
    public String thumbImgUrl;
    public String imgTitle;

    public GalleryItem (String imgUrl) {
        // this(imgUrl, null);

        // BUG:
        this(imgUrl, imgUrl);
    }

    public GalleryItem (String imgUrl, String thumbImgUrl) {
        this(imgUrl, thumbImgUrl, null);
    }

    //
    // TODO:
    //
    // - Заменить аргументы функции на читаемы
    // - Все файлы перенсти в UTF-8 кодировку
    //

    public GalleryItem (String imgUrl, String thumbImgUrl, String imgTitle) {
        this.imgUrl = imgUrl;
        this.thumbImgUrl = thumbImgUrl;
        this.imgTitle = imgTitle;

        //
        // TODO:
        //
        // - Заменить этот код на высчитывание hash суммы (crc32)
        // - Или избавиться от него, если поле id нигде не используется
        //

        StringBuilder sb = new StringBuilder();
        for (String s : new String[] {
                imgUrl, thumbImgUrl, imgTitle
            }) {
            if (s != null) {
                sb.append(s);
            }
        }

        id = String.valueOf(sb.toString().hashCode());
    }

    //
    // TODO:
    //
    // - Нужен ли id параметр? Если нет, убрать этот конструктор
    //

    public GalleryItem (String _id, String _url, String _thumb_img, String _title) {
        id = _id;
        imgUrl = _url;
        thumbImgUrl = _thumb_img;
        imgTitle = _title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getThumbImgUrl() {
        return thumbImgUrl;
    }

    public String getTitle() {
        return imgTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
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
        id = parcel.readString();
        imgUrl = parcel.readString();
        thumbImgUrl = parcel.readString();
        imgTitle = parcel.readString();
    }

}
