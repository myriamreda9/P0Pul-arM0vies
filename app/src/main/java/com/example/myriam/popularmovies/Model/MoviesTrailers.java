package com.example.myriam.popularmovies.Model;

/**
 * Created by Myriam on 3/21/2018.
 */
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesTrailers implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideosResponse> results = new ArrayList<>();
    public final static Parcelable.Creator<MoviesTrailers> CREATOR = new Creator<MoviesTrailers>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MoviesTrailers createFromParcel(Parcel in) {
            return new MoviesTrailers(in);
        }

        public MoviesTrailers[] newArray(int size) {
            return (new MoviesTrailers[size]);
        }

    }
            ;

    protected MoviesTrailers(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (com.example.myriam.popularmovies.Model.VideosResponse.class.getClassLoader()));
    }

    public MoviesTrailers() {
    }

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return 0;
    }

}