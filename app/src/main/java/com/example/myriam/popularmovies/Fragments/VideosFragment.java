package com.example.myriam.popularmovies.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myriam.popularmovies.Adapters.VideosListAdapter;
import com.example.myriam.popularmovies.Model.MoviesModel;
import com.example.myriam.popularmovies.Model.MoviesTrailers;
import com.example.myriam.popularmovies.Model.VideosResponse;
import com.example.myriam.popularmovies.R;
import com.example.myriam.popularmovies.REST.ApiClient;
import com.example.myriam.popularmovies.REST.ApiInterface;


import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.example.myriam.popularmovies.DetailsActivity.MOVIES_KEY;

/**
 * Created by Myriam on 3/24/2018.
 */


// tout faux

public class VideosFragment extends Fragment {
    ApiInterface apiInterface;
    MoviesModel movieId;
    @BindView(R.id.video_recycler_view)
    RecyclerView videoRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_fragment, container, false);

    }

    //    https://mobikul.com/pass-data-activity-fragment-android/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ButterKnife.bind(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        videoRecyclerView.setLayoutManager(mLayoutManager);
        videoRecyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MoviesTrailers> TrailersResponse = apiInterface.getMoviesVideos(movieId.getId(), com.example.myriam.popularmovies.BuildConfig.THE_MOVIE_DB_API_TOKEN);
        videoRetrofit(TrailersResponse);


    }

//fi hagga n2sa set view beta3 el recyclerview

    private void videoRetrofit(Call<MoviesTrailers> trailerResponse) {
        trailerResponse.enqueue(new Callback<MoviesTrailers>() {
            @Override
            public void onResponse(Call<MoviesTrailers> call, Response<MoviesTrailers> response) {

                List<VideosResponse> movies = response.body().getResults();
                Log.d("movies", movies.toString());

                mAdapter = new VideosListAdapter(movies, getActivity().getBaseContext());
                videoRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<MoviesTrailers> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.connectionError, Toast.LENGTH_LONG).show();
            }
        });

    }
}
