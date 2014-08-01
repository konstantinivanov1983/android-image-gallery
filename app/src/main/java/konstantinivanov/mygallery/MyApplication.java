<<<<<<< HEAD
package konstantinivanov.mygallery;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Konstantin on 01.07.2014.
 */
public class MyApplication extends Application {

    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        MyApplication.sContext = getApplicationContext();
        //initialize ImageLoader with this configuration
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    public static Context getAppContext () {
        return MyApplication.sContext;
    }
}
=======
package konstantinivanov.mygallery;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Konstantin on 01.07.2014.
 */
public class MyApplication extends Application {

    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        MyApplication.sContext = getApplicationContext();
        //initialize ImageLoader with this configuration
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    public static Context getAppContext () {
        return MyApplication.sContext;
    }
}
>>>>>>> 1970ca9b1af3033d4f4009e26097a64323f4d52d
