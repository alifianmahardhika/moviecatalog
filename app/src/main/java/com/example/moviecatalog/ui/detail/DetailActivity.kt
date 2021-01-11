package com.example.moviecatalog.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalog.R
import com.example.moviecatalog.data.MovieEntity
import com.example.moviecatalog.data.TvEntity
import com.example.moviecatalog.databinding.ActivityDetailBinding
import com.example.moviecatalog.databinding.ContentDetailBinding
import com.example.moviecatalog.ui.movies.MoviesViewModel
import com.example.moviecatalog.ui.tv.TvViewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CONTENT = "extra_content"
    }

    private lateinit var detailBinding: ContentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)
        val extras = intent.extras
        if (extras != null) {
            val itemId = extras.getString(EXTRA_CONTENT)
            if (itemId != null) {
                val movieVM = ViewModelProvider(
                    this,
                    ViewModelProvider.NewInstanceFactory()
                )[MoviesViewModel::class.java]
                val tvVm = ViewModelProvider(
                    this,
                    ViewModelProvider.NewInstanceFactory()
                )[TvViewModel::class.java]
                val movies = movieVM.getDummyMovies()
                val tvs = tvVm.getTvs()
                for (movie in movies) {
                    if (movie.movieId == itemId.toInt()) {
                        populateMovie(movie)
                    }
                }
                for (tv in tvs) {
                    if (tv.tvId == itemId) {
                        populateTv(tv)
                    }
                }
            }
        }
    }

    private fun populateTv(tv: TvEntity) {
        detailBinding.textTitle.text = tv.title
        detailBinding.textDesc.text = tv.description
        Glide.with(this)
            .load(tv.poster)
            .transform(RoundedCorners(15))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(detailBinding.imagePoster)
    }

    private fun populateMovie(movie: MovieEntity) {
        detailBinding.textTitle.text = movie.title
        detailBinding.textDesc.text = movie.description
        Glide.with(this)
            .load(movie.poster)
            .transform(RoundedCorners(15))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(detailBinding.imagePoster)
    }
}