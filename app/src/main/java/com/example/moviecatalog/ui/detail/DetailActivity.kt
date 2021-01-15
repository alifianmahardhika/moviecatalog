package com.example.moviecatalog.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalog.R
import com.example.moviecatalog.data.source.local.entity.MovieEntity
import com.example.moviecatalog.data.source.local.entity.TvEntity
import com.example.moviecatalog.databinding.ActivityDetailBinding
import com.example.moviecatalog.databinding.ContentDetailBinding
import com.example.moviecatalog.ui.movies.MoviesViewModel
import com.example.moviecatalog.ui.movies.viewmodel.ViewModelFactory
import com.example.moviecatalog.ui.tv.TvViewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CONTENT = "extra_content"
    }

    private lateinit var detailBinding: ContentDetailBinding
    private val basePoster: String = "https://image.tmdb.org/t/p/w500"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)
        val extras = intent.extras
        if (extras != null) {
            val itemId = extras.getInt(EXTRA_CONTENT)
            val tvId = extras.getInt(EXTRA_CONTENT)
            val factory = ViewModelFactory.getInstance()
            val movieVM = ViewModelProvider(this, factory)[MoviesViewModel::class.java]
            val tvVm = ViewModelProvider(this, factory)[TvViewModel::class.java]
            detailBinding.progressBar.visibility = View.VISIBLE
            movieVM.getDetailMovie(itemId).observe(this, { movies ->
                detailBinding.progressBar.visibility = View.GONE
                populateMovie(movies)
            })
            tvVm.getDetailTv(tvId).observe(this, { tvs ->
                detailBinding.progressBar.visibility = View.GONE
                populateTv(tvs)
            })
        }
    }

    private fun populateTv(tvs: List<TvEntity>) {
        for (tv in tvs) {
            detailBinding.textTitle.text = tv.title
            detailBinding.textDesc.text = tv.description
            Glide.with(this)
                .load(basePoster + tv.poster)
                .transform(RoundedCorners(15))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(detailBinding.imagePoster)
        }
    }

    private fun populateMovie(movies: List<MovieEntity>) {
        for (movie in movies) {
            detailBinding.textTitle.text = movie.title
            detailBinding.textDesc.text = movie.description
            Glide.with(this)
                .load(basePoster + movie.poster)
                .transform(RoundedCorners(15))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(detailBinding.imagePoster)
        }
    }
}