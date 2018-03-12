package com.example.myriam.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myriam.popularmovies.Model.MoviesModel;
import com.example.myriam.popularmovies.Model.MoviesResponse;
import com.example.myriam.popularmovies.REST.ApiClient;
import com.example.myriam.popularmovies.REST.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_recycler_view)
     RecyclerView posterRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int spanCount = 2;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // using the butterKnife library
        //https://github.com/codepath/android_guides/wiki/Reducing-View-Boilerplate-with-Butterknife
        ButterKnife.bind(this);
        mLayoutManager = new GridLayoutManager(this, spanCount);
        posterRecyclerView.setLayoutManager(mLayoutManager);
        posterRecyclerView.setHasFixedSize(true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<MoviesResponse> popularResponse = apiInterface.getMostPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        callRetrofit(popularResponse);


        //new FetchMoviesTask().execute(NetworkUtils.MOST_POPULAR);

    }

    // the retrofit request call
    private void callRetrofit(Call<MoviesResponse> movieResponse) {
        movieResponse.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                List<MoviesModel> movies = response.body().getResults();
                Log.d("movies", "received");
                mAdapter = new PostersListAdapter(movies, MainActivity.this);
                posterRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });


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
            callRetrofit(popularResponse);
            //new FetchMoviesTask().execute(NetworkUtils.MOST_POPULAR);

        } else if (id == R.id.high_rated) {
            //get Api of highly rated
            Call<MoviesResponse> mostRatedResponse = apiInterface.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            callRetrofit(mostRatedResponse);
            //new FetchMoviesTask().execute(NetworkUtils.HIGHEST_RATE);

        }


        return super.onOptionsItemSelected(item);
    }


//    public class FetchMoviesTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//
//            /* If there's no zip code, there's nothing to look up. */
//            if (params.length == 0) {
//                return null;
//            }
//
//            String sort = params[0];
//            URL weatherRequestUrl = NetworkUtils.buildCustomURL(sort);
//
//            try {
//                String jsonResponse = NetworkUtils
//                        .getResponseFromHttpUrl(weatherRequestUrl);
//
//
//                Log.d("net", "background");
//                return jsonResponse;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//
//        @Override
//        protected void onPostExecute(String weatherData) {
//            if (weatherData != null) {
//                Log.d("net", "onPostExe");
//            }
//        }
//    }
}
