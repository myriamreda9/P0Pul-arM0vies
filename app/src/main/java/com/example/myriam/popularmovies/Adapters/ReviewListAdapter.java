package com.example.myriam.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myriam.popularmovies.Model.ReviewsResponse;
import com.example.myriam.popularmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Myriam on 3/28/2018.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private List<ReviewsResponse> review;
    //    private final OnItemClickListener listener;

    public ReviewListAdapter(List<ReviewsResponse> reviews, Context context) {

        Context context1 = context;
        this.review = reviews;
        //this.listener = listener;
    }


//    public interface OnItemClickListener {
//        void onItemClick(String trialer);
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_adapter, parent, false);
        return new ViewHolder(linearLayout, review);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.author.setText(review.get(position).getAuthor());
        holder.content.setText(review.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return review.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        List<ReviewsResponse> reviews;

        @BindView(R.id.author_name_item)
        TextView author;
        @BindView(R.id.author_review_item)
        TextView content;


        public ViewHolder(LinearLayout view, List<ReviewsResponse> reviews) {
            super(view);
            ButterKnife.bind(this, view);
            this.reviews = reviews;

        }

    }
}

//https://antonioleiva.com/recyclerview-listener/
