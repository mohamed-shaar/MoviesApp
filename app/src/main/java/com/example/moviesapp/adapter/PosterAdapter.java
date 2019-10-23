package com.example.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.PosterClickListener;
import com.example.moviesapp.R;
import com.example.moviesapp.model.MovieResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private Context context;
    private ArrayList<String> posterList;
    private ArrayList<MovieResult> movieResults;
    private PosterClickListener posterClickListener;

    public PosterAdapter(Context context, ArrayList<String> posterList, ArrayList<MovieResult> movieResults) {
        this.context = context;
        this.posterList = posterList;
        this.movieResults = movieResults;
    }

    public PosterAdapter(Context context, ArrayList<String> posterList, ArrayList<MovieResult> movieResults, PosterClickListener posterClickListener) {
        this.context = context;
        this.posterList = posterList;
        this.movieResults = movieResults;
        this.posterClickListener = posterClickListener;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.poster_item, viewGroup, false);
        return new PosterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PosterViewHolder posterViewHolder, final int i) {
        String posterUrl = posterList.get(i);
        MovieResult movieResultObject = movieResults.get(i);
        Picasso.get().load(posterUrl).fit().centerCrop().into(posterViewHolder.iv_poster);

        ViewCompat.setTransitionName(posterViewHolder.iv_poster, movieResultObject.getTitle());


        posterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent detailIntent = new Intent(context, MovieDetailActivity.class);
                detailIntent.putExtra(EXTRA_TITLE, movieResults.get(i).getTitle());
                detailIntent.putExtra(EXTRA_RELEASE_DATE, movieResults.get(i).getReleaseDate());
                detailIntent.putExtra(EXTRA_POSTER_PATH, movieResults.get(i).getPosterPath());
                detailIntent.putExtra(EXTRA_VOTE_AVERAGE, movieResults.get(i).getVoteAverage());
                detailIntent.putExtra(EXTRA_PLOT_SYNOPSIS, movieResults.get(i).getOverview());
                detailIntent.putExtra(EXTRA_ID, movieResults.get(i).getId());*/

                posterClickListener.onPosterItemClick(
                        posterViewHolder.getAdapterPosition(),
                        movieResults.get(i),
                        posterViewHolder.iv_poster);

                //optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, manager.findViewByPosition(i), getString(R.string.transition_name));
                //ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, getString(R.string.trailer_name));
                //startActivity(detailIntent, optionsCompat.toBundle());

                //context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posterList.size();
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_poster;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster);
        }
    }
}
