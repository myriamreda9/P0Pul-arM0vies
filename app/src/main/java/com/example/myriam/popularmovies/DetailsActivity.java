package com.example.myriam.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myriam.popularmovies.Adapters.ReviewListAdapter;
import com.example.myriam.popularmovies.Adapters.VideosListAdapter;
import com.example.myriam.popularmovies.Data.MoviesContract;
import com.example.myriam.popularmovies.Data.MoviesDbHelper;
import com.example.myriam.popularmovies.Model.MoviesModel;
import com.example.myriam.popularmovies.Model.MoviesReviews;
import com.example.myriam.popularmovies.Model.MoviesTrailers;
import com.example.myriam.popularmovies.Model.ReviewsResponse;
import com.example.myriam.popularmovies.Model.VideosResponse;
import com.example.myriam.popularmovies.REST.ApiClient;
import com.example.myriam.popularmovies.REST.ApiInterface;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myriam.popularmovies.Adapters.PostersListAdapter.ITEM_SELECTED;
import static com.example.myriam.popularmovies.Adapters.PostersListAdapter.ViewHolder.ITEM_SELECTED_FAVORITE;

public class DetailsActivity extends AppCompatActivity {

    private static final int FAVORITE = 1;
    private static final String STAR_STATE = "star_state";
    private static final String REVIEW_STATE = "reviews";
    private static final String VIDEO_STATE = "videos";

    @BindView(R.id.original_title)
    TextView title;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.average_vote)
    TextView averageVote;
    @BindView(R.id.poster_image)
    ImageView posterImageView;
    ApiInterface apiInterface;
    @BindView(R.id.video_recycler_view)
    RecyclerView videoRecyclerView;
    @BindView(R.id.review_recycler_view)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.star_favorite)
    ImageButton starButton;

    private RecyclerView.Adapter mAdapter;
    private SQLiteDatabase mDb;

    private ArrayList<VideosResponse> videoArraylist = new ArrayList<>();
    private ArrayList<ReviewsResponse> reviewArraylist = new ArrayList<>();


    public static final String MOVIES_KEY = "moviesClassKey";
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private MoviesModel movie;

    private Cursor mcursor;

    private int movieID = 0;
    private int StarAdded = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        MoviesDbHelper dbHelper = new MoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();


        ButterKnife.bind(this);


        movie = getIntent().getParcelableExtra(ITEM_SELECTED);
        movieID = getIntent().getIntExtra(ITEM_SELECTED_FAVORITE, 458875);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (movieID == 458875) {
            getMovieDetail(movie);
        } else {

            Call<MoviesModel> favMovie = apiInterface.getFavoriteMovie(movieID, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            favRetrofit(favMovie);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(VIDEO_STATE, videoArraylist);
        outState.putParcelableArrayList(REVIEW_STATE, reviewArraylist);
        outState.putInt(STAR_STATE, StarAdded);

        super.onSaveInstanceState(outState);

    }

    // get the favorite movies from database if connection off
    private void favRetrofit(Call<MoviesModel> favMovie) {
        favMovie.enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                Log.d("resopnse", response.body().toString());
                movie = response.body();
                starButton.setActivated(true);
                starButton.setImageDrawable(getResources().getDrawable(R.drawable.icons_star_filled));

                getMovieDetail(movie);
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("resopnseError", "error ya nas ");
                MoviesDbHelper db = new MoviesDbHelper(getApplicationContext());
                mDb = db.getReadableDatabase();
                starButton.setImageDrawable(getResources().getDrawable(R.drawable.icons_star_filled));

                mcursor = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                        null,
                        MoviesContract.MoviesEntry.COLUMN_MOVIE_ID + " = " + movieID,
                        null,
                        null);


                starButton.setActivated(true);
                int movieIndex = mcursor.getColumnIndex(MoviesContract.MoviesEntry._ID);
                mcursor.moveToPosition(movieIndex);


                int movieTitleID = mcursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE);
                String movieTitle = mcursor.getString(movieTitleID);

                int movieOverViewID = mcursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW);
                String moviesOverView = mcursor.getString(movieOverViewID);


                title.setText(movieTitle);
                overview.setText(moviesOverView);


                Toast.makeText(DetailsActivity.this, R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });

    }

    // Function to set the UI
    private void getMovieDetail(MoviesModel movieDetail) {
        URL imageUrl = NetworkUtils.buildPhotosURL(movieDetail.getPosterPath());
        mcursor = null;

        mcursor = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                MoviesContract.MoviesEntry.COLUMN_MOVIE_ID + " = " + movieDetail.getId(),
                null,
                null);


            int movieIndex = mcursor.getColumnIndex(MoviesContract.MoviesEntry._ID);

            if (mcursor.moveToPosition(movieIndex)) {
                mcursor.moveToPosition(movieIndex);
                int favoriteMovie = mcursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_FAVORITE);
                int moviefavorite = mcursor.getInt(favoriteMovie);


                if (moviefavorite == FAVORITE) {
                    starButton.setActivated(true);
                    starButton.setImageDrawable(getResources().getDrawable(R.drawable.icons_star_filled));
                }
            }

        Picasso.with(this)
                .load(imageUrl.toString())
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(posterImageView);

        title.setText(movieDetail.getOriginalTitle());
        overview.setText(movieDetail.getOverview());
        releaseDate.setText(movieDetail.getReleaseDate());
        averageVote.setText(String.valueOf(movieDetail.getVoteAverage()));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        videoRecyclerView.setLayoutManager(mLayoutManager);
        videoRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewRecyclerView.setHasFixedSize(true);


        Call<MoviesTrailers> TrailersResponse = apiInterface.getMoviesVideos(movieDetail.getId(), BuildConfig.THE_MOVIE_DB_API_TOKEN);
        videoRetrofit(TrailersResponse);

        Call<MoviesReviews> ReviewsResponse = apiInterface.getMoviesReviews(movieDetail.getId(), BuildConfig.THE_MOVIE_DB_API_TOKEN);
        reviewRetrofit(ReviewsResponse);

    }

    //save favorite movies in the database and change the star color
    @OnClick
    public void onToggleStar(View view) {

        if (!starButton.isActivated() && StarAdded != FAVORITE) {
            addFavoriteMovie(movie);
            starButton.setImageDrawable(getResources().getDrawable(R.drawable.icons_star_filled));
            StarAdded = FAVORITE;
        } else {
            deleteFromFavorites(movie.getId());
            Log.d("star", "starClickedDeleted");
            starButton.setImageDrawable(getResources().getDrawable(R.drawable.icons_star));
            StarAdded = 0;
        }


    }

    // get the trailers
    private void videoRetrofit(Call<MoviesTrailers> trailerResponse) {
        trailerResponse.enqueue(new Callback<MoviesTrailers>() {
            @Override
            public void onResponse(Call<MoviesTrailers> call, Response<MoviesTrailers> response) {

                List<VideosResponse> movies = response.body().getResults();
                Log.d("movies", movies.toString());

                videoArraylist = (ArrayList<VideosResponse>) movies;
                mAdapter = new VideosListAdapter(movies, DetailsActivity.this);
                videoRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<MoviesTrailers> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });

    }

    // get the reviews
    private void reviewRetrofit(Call<MoviesReviews> reviewsResponse) {
        reviewsResponse.enqueue(new Callback<MoviesReviews>() {
            @Override
            public void onResponse(Call<MoviesReviews> call, Response<MoviesReviews> response) {

                List<ReviewsResponse> reviews = response.body().getResults();
//                Log.d("movies", response.toString());
                reviewArraylist = (ArrayList<ReviewsResponse>) reviews;
                mAdapter = new ReviewListAdapter(reviews, DetailsActivity.this);
                reviewRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MoviesReviews> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });

    }


    // adding the movies to database using the content provider from udacity cource
    private void addFavoriteMovie(MoviesModel moviesModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID, moviesModel.getId());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE, moviesModel.getOriginalTitle());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER, moviesModel.getPosterPath());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW, moviesModel.getOverview());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_FAVORITE, FAVORITE);
        Uri uri = getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            Log.d(LOG_TAG, uri.toString());
        }
    }


    // delete from the database
    private void deleteFromFavorites(int id) {
        String stringId = Integer.toString(id);
        Uri uri = MoviesContract.MoviesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        getContentResolver().delete(uri, null, null);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}