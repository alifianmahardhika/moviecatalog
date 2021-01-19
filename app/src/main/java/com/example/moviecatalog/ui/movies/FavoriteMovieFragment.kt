package com.example.moviecatalog.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalog.databinding.FragmentFavoriteMovieBinding
import com.example.moviecatalog.viewmodel.ViewModelFactory

class FavoriteMovieFragment : Fragment() {
    private lateinit var favoriteMovieBinding: FragmentFavoriteMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteMovieBinding =
            FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return favoriteMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]
            val movieAdapter = MovieAdapter()
            favoriteMovieBinding.progressBar.visibility = View.VISIBLE
            viewModel.getFavoriteMovies().observe(this, { movies ->
                if (movies != null) {
                    favoriteMovieBinding.progressBar.visibility = View.GONE
                    movieAdapter.submitList(movies)
                    movieAdapter.notifyDataSetChanged()
                }
            })

            with(favoriteMovieBinding.rvFavoriteMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}