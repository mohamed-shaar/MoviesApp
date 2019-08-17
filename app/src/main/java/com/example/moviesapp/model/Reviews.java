package com.example.moviesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Reviews {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("results")
    @Expose
    private List<ReviewResult> results = new ArrayList<ReviewResult>();
    @SerializedName("total_pages")
    @Expose
    private long totalPages;
    @SerializedName("total_results")
    @Expose
    private long totalResults;

    /**
     * No args constructor for use in serialization
     *
     */
    public Reviews() {
    }

    /**
     *
     * @param id
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public Reviews(long id, long page, List<ReviewResult> results, long totalPages, long totalResults) {
        super();
        this.id = id;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Reviews withId(long id) {
        this.id = id;
        return this;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public Reviews withPage(long page) {
        this.page = page;
        return this;
    }

    public List<ReviewResult> getResults() {
        return results;
    }

    public void setResults(List<ReviewResult> results) {
        this.results = results;
    }

    public Reviews withResults(List<ReviewResult> results) {
        this.results = results;
        return this;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public Reviews withTotalPages(long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public Reviews withTotalResults(long totalResults) {
        this.totalResults = totalResults;
        return this;
    }
}
