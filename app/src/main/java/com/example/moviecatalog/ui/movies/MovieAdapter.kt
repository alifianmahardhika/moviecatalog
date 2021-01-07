package com.example.moviecatalog.ui.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalog.R
import com.example.moviecatalog.data.MovieEntity
import com.example.moviecatalog.databinding.ItemsMovieBinding
import com.example.moviecatalog.ui.detail.DetailActivity

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var listMovies = ArrayList<MovieEntity>()

    fun setMovies(movies: List<MovieEntity>) {
        this.listMovies.clear()
        this.listMovies.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = listMovies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    class MovieViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                movieTitle.text = movie.title
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_CONTENT, movie.movieId)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(movie.poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(moviePoster)
            }
        }
    }
}
