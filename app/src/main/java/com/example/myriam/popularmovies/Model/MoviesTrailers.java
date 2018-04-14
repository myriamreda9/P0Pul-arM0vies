package com.example.myriam.popularmovies.Model;

/**
 * Created by Myriam on 3/21/2018.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesTrailers {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideosResponse> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideosResponse> getResults() {
        return results;
    }

    public void setResults(List<VideosResponse> results) {
        this.results = results;
    }

}