package com.example.moviecatalog.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        const val EXTRA_TAG = "extra_tag"
    }

    private lateinit var detailBinding: ContentDetailBinding
    private lateinit var movieVM: MoviesViewModel
    private lateinit var tvVm: TvViewModel
    private var menu: Menu? = null
    private val basePoster: String = "https://image.tmdb.org/t/p/w500"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailBinding = activityDetailBinding.detailContent

        setContentView(activityDetailBinding.root)

        val extras = intent.extras
        if (extras != null) {
            val itemId = extras.getInt(EXTRA_CONTENT)
            val extraTag = extras.getInt(EXTRA_TAG)
            val factory = ViewModelFactory.getInstance(this)
            if (extraTag == 0) {
                movieVM = ViewModelProvider(this, factory)[MoviesViewModel::class.java]
                movieVM.setMovieId(itemId)
                movieVM.selectedMovie.observe(this, { movies ->
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
            }
            if (extraTag == 1) {
                tvVm = ViewModelProvider(this, factory)[TvViewModel::class.java]
                tvVm.setTvId(itemId)
                tvVm.selectedTv.observe(this, { tvs ->
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        val extras = intent.extras
        if (extras != null) {
            val extraTag = extras.getInt(EXTRA_TAG)
            if (extraTag == 0) {
                movieVM.selectedMovie.observe(this, { movies ->
                    if (movies != null) {
                        when (movies.status) {
                            Status.LOADING -> detailBinding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> if (movies.data != null) {
                                detailBinding.progressBar.visibility = View.GONE
                                val stateMovie = movies.data.isFavorite
                                setFavoriteState(stateMovie)
                            }
                            Status.ERROR -> {
                                detailBinding.progressBar.visibility = View.GONE
                                toastError(this)
                            }
                        }
                    }
                })
            }
            if (extraTag == 1) {
                tvVm.selectedTv.observe(this, { tvs ->
                    if (tvs != null) {
                        when (tvs.status) {
                            Status.LOADING -> detailBinding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                detailBinding.progressBar.visibility = View.GONE
                                val stateTv = tvs.data?.isFavorite
                                if (stateTv != null) {
                                    setFavoriteState(stateTv)
                                }
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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val extras = intent.extras
        if (extras != null) {
            val extraTag = extras.getInt(EXTRA_TAG)
            if (item.itemId == R.id.action_bookmark) {
                if (extraTag == 0) {
                    movieVM.setFavoriteMovie()
                    return true
                }
                if (extraTag == 1) {
                    tvVm.setFavorite()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_unfavorite)
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