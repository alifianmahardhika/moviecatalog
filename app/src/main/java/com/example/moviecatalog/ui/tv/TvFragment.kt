package com.example.moviecatalog.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalog.databinding.FragmentTvBinding

class TvFragment : Fragment() {
    lateinit var fragmentTvBinding: FragmentTvBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvBinding = FragmentTvBinding.inflate(inflater, container, false)
        return fragmentTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[TvViewModel::class.java]
            val tvs = viewModel.getTvs()
            val tvAdapter = TvAdapter()
            tvAdapter.setTvs(tvs)

            with(fragmentTvBinding.rvTv) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }
        }
    }
}