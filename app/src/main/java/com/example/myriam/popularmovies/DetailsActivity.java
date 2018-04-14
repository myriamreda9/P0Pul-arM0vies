package com.example.myriam.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myriam.popularmovies.Model.MoviesModel;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.myriam.popularmovies.PostersListAdapter.ITEM_SELECTED;

public class DetailsActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        MoviesModel movie = (MoviesModel) getIntent().getSerializableExtra(ITEM_SELECTED);
        Log.d("details", movie.getTitle());

        URL imageUrl = NetworkUtils.buildPhotosURL(movie.getPosterPath());

        Picasso.with(this)
                .load(imageUrl.toString())
                .into(posterImageView);

        title.setText(movie.getOriginalTitle());
        overview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
        averageVote.setText(String.valueOf(movie.getVoteAverage()));

    }

    @Override
    public void onBackPressed() {
    super.onBackPressed();
    this.finish();
    }

}
