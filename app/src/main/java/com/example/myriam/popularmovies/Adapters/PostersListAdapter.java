package com.example.myriam.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.myriam.popularmovies.Data.MoviesContract;
import com.example.myriam.popularmovies.DetailsActivity;
import com.example.myriam.popularmovies.Model.MoviesModel;
import com.example.myriam.popularmovies.NetworkUtils;
import com.example.myriam.popularmovies.PicassoCaches;
import com.example.myriam.popularmovies.R;
import com.squareup.picasso.Picasso;


import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PostersListAdapter extends RecyclerView.Adapter<PostersListAdapter.ViewHolder> {

    public static final String ITEM_SELECTED = "Movies model";
    private List<MoviesModel> movies;
    private Context context;
    private Cursor mCursor;

    public PostersListAdapter(List<MoviesModel> moviesModels, Context context, Cursor cursor) {

        this.context = context;
        this.movies = moviesModels;
        this.mCursor = cursor;

    }


    @NonNull
    @Override
    public PostersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poster_list_view, parent, false);

        return new ViewHolder(imageView, movies,mCursor);
    }

    @Override
    public void onBindViewHolder(PostersListAdapter.ViewHolder holder, int position) {
        URL imageUrl;
        if (mCursor != null) {

            int posterIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER);

            mCursor.moveToPosition(position); // get to the right location in the cursor

            // Determine the values of the wanted data
            String posterPath = mCursor.getString(posterIndex);
            imageUrl = NetworkUtils.buildPhotosURL(posterPath);
        } else {
            imageUrl = NetworkUtils.buildPhotosURL(movies.get(position).getPosterPath());
        }
        PicassoCaches
                .getPicassoInstance(context)
                .load(imageUrl.toString())
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(holder.poster);

//        Picasso.with(context)
//                .load(imageUrl.toString()).error(R.drawable.ic_error_outline_black_24dp)
//                .into(holder.poster)
//        ;

    }


    @Override
    public long getItemId(int i) {
        return movies.get(i).getId();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public static final String ITEM_SELECTED_FAVORITE = "favorite Item selected";
        List<MoviesModel> model;

        Cursor cursor;
        @BindView(R.id.poster_list_item)
        ImageView poster;

        public ViewHolder(ImageView view, List<MoviesModel> model, Cursor cursor) {
            super(view);
            ButterKnife.bind(this, view);
            this.model = model;
            this.cursor = cursor;

        }


        //https://antonioleiva.com/recyclerview-listener/
        @OnClick
        public void onClick(View view) {
            int id = getAdapterPosition();
            Intent detailIntent = new Intent(view.getContext(), DetailsActivity.class);
            if (cursor != null && model == null ) {
                int movieIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID);
                cursor.moveToPosition(id);
                 int moviesId = cursor.getInt(movieIndex);
                detailIntent.putExtra(ITEM_SELECTED_FAVORITE, moviesId);
            } else {
                detailIntent.putExtra(ITEM_SELECTED, model.get(id));
            }
            view.getContext().startActivity(detailIntent);

        }

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (movies == null && mCursor == null) {
            return 0;
        } else if (mCursor == null) return movies.size();
        else return mCursor.getCount();
    }


//    //mock data
//    private Integer[] mThumbIds = {
//            R.drawable.sample_5,
//            R.drawable.sample_5,
//             R.drawable.sample_7,
//            R.drawable.sample_5,
//             R.drawable.sample_7
//    };

}
