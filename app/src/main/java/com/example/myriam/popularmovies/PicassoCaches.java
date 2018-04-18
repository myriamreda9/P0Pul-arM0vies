package com.example.myriam.popularmovies;

import android.content.Context;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Myriam on 4/18/2018.
 */

public class PicassoCaches {

    private static Picasso picassoInstance = null;
    private PicassoCaches (Context context) {

        Downloader downloader   = new OkHttpDownloader(context, Integer.MAX_VALUE);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(downloader);

        picassoInstance = builder.build();
    }

    public static Picasso getPicassoInstance (Context context) {

        if (picassoInstance == null) {

            new PicassoCaches(context);
            return picassoInstance;
        }

        return picassoInstance;
    }
    //https://stackoverflow.com/questions/23978828/how-do-i-use-disk-caching-in-picasso

}
