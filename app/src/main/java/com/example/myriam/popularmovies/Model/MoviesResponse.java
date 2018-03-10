package com.example.myriam.popularmovies.Model;

/**
 * Created by Myriam on 3/9/2018.
 */
import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


 public class MoviesResponse implements Serializable
    {

        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("total_results")
        @Expose
        private Integer totalResults;
        @SerializedName("total_pages")
        @Expose
        private Integer totalPages;
        @SerializedName("results")
        @Expose
        private List<MoviesModel> results = null;

        public MoviesResponse() {
        }

        public MoviesResponse(Integer page, Integer totalResults, Integer totalPages, List<MoviesModel> results) {
            super();
            this.page = page;
            this.totalResults = totalResults;
            this.totalPages = totalPages;
            this.results = results;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public List<MoviesModel> getResults() {
            return results;
        }

        public void setResults(List<MoviesModel> results) {
            this.results = results;
        }

    }

