package com.example.moviecatalog.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.moviecatalog.ui.tv.TvViewModel
import com.example.moviecatalog.viewmodel.ViewModelFactory
import com.example.moviecatalog.vo.Status

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
            val factory = ViewModelFactory.getInstance(this)
            val movieVM = ViewModelProvider(this, factory)[MoviesViewModel::class.java]
            val tvVm = ViewModelProvider(this, factory)[TvViewModel::class.java]
            movieVM.getDetailMovie(itemId).observe(this, { movies ->
                if (movies != null) {
                    when (movies.status) {
                        Status.LOADING -> detailBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> if (movies.data != null) {
                            detailBinding.progressBar.visibility = View.GONE
                            populateMovie(movies.data)
                        }
                        Status.ERROR -> {
                            detailBinding.progressBar.visibility = View.GONE
                            toastError(this)
                        }
                    }
                }
            })
            tvVm.getDetailTv(tvId).observe(this, { tvs ->
                if (tvs != null) {
                    when (tvs.status) {
                        Status.LOADING -> detailBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            detailBinding.progressBar.visibility = View.GONE
                            tvs.data?.let { populateTv(it) }
                        }
                        Status.ERROR -> {
                            detailBinding.progressBar.visibility = View.GONE
                            toastError(this)
                        }
                    }
                }
            })
        }
    }

    private fun populateTv(tv: TvEntity) {
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

    private fun populateMovie(movie: MovieEntity) {
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

    private fun toastError(context: Context) {
        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
    }
}