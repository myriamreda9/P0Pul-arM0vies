package com.example.myriam.popularmovies;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myriam.popularmovies.Adapters.PostersListAdapter;
import com.example.myriam.popularmovies.Data.MoviesContract;
import com.example.myriam.popularmovies.Model.MoviesModel;
import com.example.myriam.popularmovies.Model.MoviesResponse;
import com.example.myriam.popularmovies.REST.ApiClient;
import com.example.myriam.popularmovies.REST.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String STATE_KEY = "stateKey";
    private static final String PAGE = "page";
    @BindView(R.id.my_recycler_view)
    RecyclerView posterRecyclerView;
    private RecyclerView.Adapter RecyclerAdapter;
    private ApiInterface apiInterface;
    private int flag = 0;
    private SharedPreferences preferences;

    ArrayList<MoviesModel> moviesModel = new ArrayList<MoviesModel>();
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    int page = 0;

    private static final int TASK_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //using the butterKnife library
        //https://github.com/codepath/android_guides/wiki/Reducing-View-Boilerplate-with-Butterknife
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, numberOfColumns());
        posterRecyclerView.setLayoutManager(mLayoutManager);
        posterRecyclerView.setHasFixedSize(true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        preferences = this.getSharedPreferences(PAGE,Context.MODE_PRIVATE);
        page = preferences.getInt(PAGE, Context.MODE_PRIVATE);

        if (savedInstanceState == null || !savedInstanceState.containsKey(STATE_KEY)) {
            if (page != 0 ) {
                setView(page);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(PAGE, 0);
                editor.apply();
            } else {
                Call<MoviesResponse> popularResponse = apiInterface.getMostPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
                callRetrofit(popularResponse);
            }
        } else {
            moviesModel = savedInstanceState.getParcelableArrayList(STATE_KEY);
            RecyclerAdapter = new PostersListAdapter(moviesModel, MainActivity.this, null);
            posterRecyclerView.setAdapter(RecyclerAdapter);

        }

    }

    // saves the state of the activty in a bundle to be retrievd when recreated
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_KEY, moviesModel);
        super.onSaveInstanceState(outState);

    }

    // the retrofit request call
    private void callRetrofit(Call<MoviesResponse> movieResponse) {

        movieResponse.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                List<MoviesModel> movies = response.body().getResults();
                Log.d("movies", "received");
                moviesModel = (ArrayList<MoviesModel>) movies;
                RecyclerAdapter = new PostersListAdapter(movies, MainActivity.this, null);
                posterRecyclerView.setAdapter(RecyclerAdapter);

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });

    }

    // https://inthecheesefactory.com/blog/fragment-state-saving-best-practices/en
    // restore the activity state when back pressed or filter selected
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        moviesModel = savedInstanceState.getParcelableArrayList(STATE_KEY);
        RecyclerAdapter = new PostersListAdapter(moviesModel, MainActivity.this, null);
        posterRecyclerView.setAdapter(RecyclerAdapter);

    }

    // for dynamically set the columns
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    private void setView(int flag) {
        if (flag == 1) {
            Call<MoviesResponse> popularResponse = apiInterface.getMostPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            callRetrofit(popularResponse);
        } else if (flag == 2) {
            Call<MoviesResponse> mostRatedResponse = apiInterface.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            callRetrofit(mostRatedResponse);
        } else if (flag == 3) {
            getAllFavorites();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.most_popular) {
            //get Api of most popular
            Call<MoviesResponse> popularResponse = apiInterface.getMostPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            flag = 1;
            callRetrofit(popularResponse);
            //new FetchMoviesTask().execute(NetworkUtils.MOST_POPULAR);
        } else if (id == R.id.high_rated) {
            //get Api of highly rated
            Call<MoviesResponse> mostRatedResponse = apiInterface.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            flag = 2;
            callRetrofit(mostRatedResponse);

        } else if (id == R.id.favorite_movies) {
            //get Api of favorite movies
            flag = 3;
            getAllFavorites();

        }
        // From android developer
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PAGE, flag);
        editor.apply();
        return super.onOptionsItemSelected(item);
    }

    private void getAllFavorites() {
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

    }

    // from the course
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mData = null;

            @Override
            protected void onStartLoading() {
                if (mData != null) {
                    deliverResult(mData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {

                    return getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MoviesContract.MoviesEntry._ID);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
//       if (!moviesModel.isEmpty())
        moviesModel.clear();
        for (int j = 0; j < data.getCount(); j++) {
            MoviesModel model = new MoviesModel();
            data.moveToPosition(j);
            model.setPosterPath(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER)));
            model.setOriginalTitle(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE)));
            model.setOverview(data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW)));
            model.setId(data.getInt(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID)));
            moviesModel.add(model);
        }

        RecyclerAdapter = new PostersListAdapter(null, MainActivity.this, data);
        posterRecyclerView.setAdapter(RecyclerAdapter);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}