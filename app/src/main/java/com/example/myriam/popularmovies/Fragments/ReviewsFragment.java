package com.example.myriam.popularmovies.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myriam.popularmovies.R;
/**
 * Created by Myriam on 3/24/2018.
 */

public class ReviewsFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.review_fragment, container, false);
    }
}
