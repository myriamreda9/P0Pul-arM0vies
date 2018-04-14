package com.example.myriam.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myriam.popularmovies.Model.VideosResponse;
import com.example.myriam.popularmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Myriam on 3/27/2018.
 */

public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.ViewHolder>  {

    private List<VideosResponse> trailer;
    //    private final OnItemClickListener listener;

    public VideosListAdapter(List<VideosResponse> trailers, Context context) {

        Context context1 = context;
        this.trailer = trailers;
        //this.listener = listener;
    }



//    public interface OnItemClickListener {
//        void onItemClick(String trialer);
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView  textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videos_list_view, parent, false);
        return new ViewHolder(textView,trailer);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.trialer.setText(trailer.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return trailer.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        List<VideosResponse> trailers;

        @BindView(R.id.video_list_item)
        TextView trialer;

//https://blog.stylingandroid.com/compound-drawable/
        public TextView setTrialer() {
            trialer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.youtibe_icon,0,0,0);
            return trialer;
        }

        public ViewHolder(TextView view, List<VideosResponse> trailer) {
            super(view);
            ButterKnife.bind(this, view);
            this.trailers = trailer;
            setTrialer();
        }

        @OnClick
        public void onClick(View view) {

            int id = getAdapterPosition();
            // case not youtube
            String url = "https://www.google.com"+"/search?q="+trailers.get(id).getName();
            if (trailers.get(id).getSite().equals("YouTube")){
                url ="https://www.youtube.com/watch?v="+trailers.get(id).getKey();
            }
            Uri webpage = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                view.getContext().startActivity(intent);
            }

        }
    }
}

        //https://antonioleiva.com/recyclerview-listener/


