package com.konstantinivanov.androidimagegallerylibrary.models;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by Администратор on 13.08.2014.
 */
public class ImageLoad {

    public ImageLoad() {}

    public static void getImage(Drawable drawable, String url, ImageView view) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .imageScaleType(ImageScaleType.NONE)
                .build();
        ImageLoader.getInstance().displayImage(url, view, options);
    }

}
