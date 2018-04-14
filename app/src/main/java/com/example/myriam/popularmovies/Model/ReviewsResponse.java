package com.example.myriam.popularmovies.Model;

/**
 * Created by Myriam on 3/28/2018.
 */
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReviewsResponse implements Parcelable
    {

        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("url")
        @Expose
        private String url;
        public final static Parcelable.Creator<ReviewsResponse> CREATOR = new Creator<ReviewsResponse>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public ReviewsResponse createFromParcel(Parcel in) {
                return new ReviewsResponse(in);
            }

            public ReviewsResponse[] newArray(int size) {
                return (new ReviewsResponse[size]);
            }

        }
                ;

        protected ReviewsResponse(Parcel in) {
            this.author = ((String) in.readValue((String.class.getClassLoader())));
            this.content = ((String) in.readValue((String.class.getClassLoader())));
            this.id = ((String) in.readValue((String.class.getClassLoader())));
            this.url = ((String) in.readValue((String.class.getClassLoader())));
        }

        public ReviewsResponse() {
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(author);
            dest.writeValue(content);
            dest.writeValue(id);
            dest.writeValue(url);
        }

        public int describeContents() {
            return 0;
        }

    }
