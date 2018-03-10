package com.example.myriam.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.myriam.popularmovies.Model.MoviesModel;
import com.squareup.picasso.Picasso;


import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PostersListAdapter extends RecyclerView.Adapter<PostersListAdapter.ViewHolder>    {

    public static final String ITEM_SELECTED ="Movies model" ;
    private List<MoviesModel> movies;
    private Context context;


    public PostersListAdapter(List<MoviesModel> moviesModels, Context context) {

        this.context = context ;
        this.movies = moviesModels;
    }

    @NonNull
    @Override
    public PostersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poster_list_view, parent, false);

        return new ViewHolder(imageView,movies);
    }

    @Override
    public void onBindViewHolder( PostersListAdapter.ViewHolder holder, int position) {
        URL imageUrl = NetworkUtils.buildPhotosURL(movies.get(position).getPosterPath());

        Picasso.with(context)
                .load(imageUrl.toString()).error(R.drawable.ic_error_outline_black_24dp)
                .into(holder.poster)
        ;

         }


    @Override
    public long getItemId(int i) {
        return movies.get(i).getId();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder  {

        List<MoviesModel> model ;

        @BindView(R.id.poster_list_item) ImageView poster;
        public ViewHolder(ImageView view,List<MoviesModel> model) {
            super(view);
            ButterKnife.bind(this, view);
            this.model = model;

        }

       @OnClick
        public void onClick(View view) {
            int id = getAdapterPosition();
           Intent detailIntent = new Intent(view.getContext(),DetailsActivity.class);
           detailIntent.putExtra(ITEM_SELECTED,model.get(id));

           Log.d("mobile",id+"");
           Log.d("mobile",model.get(id).getTitle());
           view.getContext().startActivity(detailIntent);

       }

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movies.size();
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
