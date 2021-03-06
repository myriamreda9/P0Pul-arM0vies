package com.example.myriam.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Myriam on 3/5/2018.
 */

public class NetworkUtils {


    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String POPULAR_MOVIES_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE = "w500//";

    private static final String API_KEY = "api_key";
    private static final String FILTER_MOVIES_BASE_URL = "http://api.themoviedb.org/3/";
    private static final String REQ_PARAM = "?";

    public static final String MOST_POPULAR = "movie/popular";
    public static final String HIGHEST_RATE = "movie/top_rated";

    public static final String MOVIES_REVIEWS = "/3/movie/{id}/reviews";
    public static final String MOVIES_VIDEOS = "/3/movie/{id}/videos";
    public static final String MOVIE_FAV = "movie/{id}";


    public static URL buildPhotosURL(String moviePoster) {
        Uri builtUri = Uri.parse(POPULAR_MOVIES_BASE_URL).buildUpon()
                .appendEncodedPath(POSTER_SIZE)
                .appendEncodedPath(moviePoster)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


// --Commented out by Inspection START (4/14/2018 4:57 PM):
//    public static URL buildCustomURL(String sortType) {
//        Uri builtUri = Uri.parse(FILTER_MOVIES_BASE_URL).buildUpon()
//                .appendEncodedPath(sortType)
//                .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_TOKEN)
//                .build();
//
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        Log.v("mobile", "Built URI " + url);
//
//        return url;
//    }
// --Commented out by Inspection STOP (4/14/2018 4:57 PM)

// --Commented out by Inspection START (4/14/2018 4:57 PM):
//    public static String getResponseFromHttpUrl(URL url) throws IOException {
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            InputStream in = urlConnection.getInputStream();
//
//            Scanner scanner = new Scanner(in);
//            boolean hasInput = scanner.hasNext();
//            if (hasInput) {
//                return scanner.next();
//            } else {
//                return null;
//            }
//        } finally {
//            urlConnection.disconnect();
//        }
//    }
// --Commented out by Inspection STOP (4/14/2018 4:57 PM)

}
