package konstantinivanov.mygallery.models;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by Konstantin on 15.07.2014.
 */
public class ImageLoad {

    static ImageLoader sImageLoader;

    public ImageLoad() {}

    public static void getImage(Drawable drawable, String url, ImageView view) {
        sImageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .imageScaleType(ImageScaleType.NONE)    //add 22.07
                .build();
        sImageLoader.displayImage(url, view, options);
    }
}
