package com.example.moviecatalog.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalog.databinding.FragmentFavoriteTvBinding
import com.example.moviecatalog.viewmodel.ViewModelFactory

class FavoriteTvFragment : Fragment() {
    private lateinit var favoriteTvBinding: FragmentFavoriteTvBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteTvBinding = FragmentFavoriteTvBinding.inflate(inflater, container, false)
        return favoriteTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]
            val tvAdapter = TvAdapter()
            favoriteTvBinding.progressBar.visibility = View.VISIBLE
            viewModel.getFavoriteTv().observe(this, { tvs ->
                if (tvs != null) {
                    favoriteTvBinding.progressBar.visibility = View.GONE
                    tvAdapter.submitList(tvs)
                    tvAdapter.notifyDataSetChanged()
                }
            })

            with(favoriteTvBinding.rvFavoriteTv) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }
        }
    }
}