package com.example.myriam.popularmovies.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myriam.popularmovies.Model.MoviesModel;
import com.example.myriam.popularmovies.NetworkUtils;
import com.example.myriam.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.net.URL;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.myriam.popularmovies.DetailsActivity.MOVIES_KEY;

/**
 * Created by Myriam on 3/24/2018.
 */

public class DetailsFragment extends Fragment {

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

    MoviesModel movie = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.detail_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());

        if (getArguments() != null) {
            movie = getArguments().getParcelable(MOVIES_KEY);
        }
        Log.d("details", movie.getTitle());


        URL imageUrl = NetworkUtils.buildPhotosURL(movie.getPosterPath());

        Picasso.with(getActivity())
                .load(imageUrl.toString())
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(posterImageView);

        title.setText(movie.getOriginalTitle());
        overview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        averageVote.setText(String.valueOf(movie.getVoteAverage()));

    }

}
