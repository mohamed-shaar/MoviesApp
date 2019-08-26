package com.example.moviesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Videos {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("results")
    @Expose
    private List<VideoResult> videoResults = new ArrayList<VideoResult>();

    /**
     * No args constructor for use in serialization
     */
    public Videos() {
    }

    /**
     * @param id
     * @param videoResults
     */
    public Videos(long id, List<VideoResult> videoResults) {
        super();
        this.id = id;
        this.videoResults = videoResults;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Videos withId(long id) {
        this.id = id;
        return this;
    }

    public List<VideoResult> getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(List<VideoResult> videoResults) {
        this.videoResults = videoResults;
    }

    public Videos withVideoResults(List<VideoResult> videoResults) {
        this.videoResults = videoResults;
        return this;
    }
}
