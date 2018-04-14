package com.example.myriam.popularmovies.REST;

import com.example.myriam.popularmovies.Model.MoviesModel;
import com.example.myriam.popularmovies.Model.MoviesResponse;
import com.example.myriam.popularmovies.Model.MoviesReviews;
import com.example.myriam.popularmovies.Model.MoviesTrailers;
import com.example.myriam.popularmovies.NetworkUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Myriam on 3/9/2018.
 */

public interface ApiInterface {
    @GET(NetworkUtils.HIGHEST_RATE)
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET(NetworkUtils.MOST_POPULAR)
    Call<MoviesResponse> getMostPopularMovies(@Query("api_key") String apiKey);

    @GET(NetworkUtils.MOVIES_REVIEWS)
    Call<MoviesReviews> getMoviesReviews(@Path("id") int id, @Query("api_key") String apiKey);

    @GET(NetworkUtils.MOVIES_VIDEOS)
    Call<MoviesTrailers> getMoviesVideos(@Path("id") int id, @Query("api_key") String apiKey);

    @GET(NetworkUtils.MOVIE_FAV)
    Call<MoviesModel> getFavoriteMovie(@Path("id") int id, @Query("api_key") String apiKey);

}
