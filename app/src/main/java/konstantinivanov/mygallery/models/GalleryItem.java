<<<<<<< HEAD
package konstantinivanov.mygallery.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Konstantin on 30.06.2014.
 */
public class GalleryItem implements Parcelable{

    public String id;
    public String img_url;
    public String thumb_img_url;
    public String title;

    public GalleryItem (String _id, String _url, String _thumb_img, String _title) {
        id = _id;
        img_url = _url;
        thumb_img_url = _thumb_img;
        title = _title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(img_url);
        parcel.writeString(thumb_img_url);
        parcel.writeString(title);
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
        img_url = parcel.readString();
        thumb_img_url = parcel.readString();
        title = parcel.readString();
    }
}
=======
package konstantinivanov.mygallery.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Konstantin on 30.06.2014.
 */
public class GalleryItem implements Parcelable{

    public String id;
    public String img_url;
    public String thumb_img_url;
    public String title;

    public GalleryItem (String _id, String _url, String _thumb_img, String _title) {
        id = _id;
        img_url = _url;
        thumb_img_url = _thumb_img;
        title = _title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(img_url);
        parcel.writeString(thumb_img_url);
        parcel.writeString(title);
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
        img_url = parcel.readString();
        thumb_img_url = parcel.readString();
        title = parcel.readString();
    }
}
>>>>>>> 1970ca9b1af3033d4f4009e26097a64323f4d52d
