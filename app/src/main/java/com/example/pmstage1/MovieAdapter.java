package com.example.pmstage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PosterViewHolder> {

    private static final String BASE_URL = "https://image.tmdb.org/t/p/w342";
    private List<Movie> movieList;
    private Movie movie;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList, ListItemClickListener listener){
        this.movieList = movieList;
        mOnClickListener = listener;
    }

    public void addMovies(List<Movie> movieList) {
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }
    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        PosterViewHolder viewHolder = new PosterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        movie = this.movieList.get(position);
        //Log.d(TAG, "#" + movie.getPoster());
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder {

        ImageView moviePosterView;

        private PosterViewHolder(View itemView) {
            super(itemView);
            moviePosterView = itemView.findViewById(R.id.iv_movie_poster);
        }

        void bind(final Movie movie) {
            Picasso.with(moviePosterView.getContext())
                    .load(BASE_URL + movie.getPoster())
                    .into(moviePosterView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.onListItemClick(movie);
                }
            });
        }
    }
}
