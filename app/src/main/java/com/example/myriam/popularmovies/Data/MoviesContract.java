package com.example.myriam.popularmovies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Myriam on 3/30/2018.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.example.myriam.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //check the directory
    public static final String PATH_FAVORITES = MoviesEntry.TABLE_NAME;

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorites";
        public static final String _ID = "ID";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_POSTER = "moviePoster";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_FAVORITE = "favorite";
    }
}
