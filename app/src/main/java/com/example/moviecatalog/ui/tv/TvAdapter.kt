package com.example.moviecatalog.ui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalog.R
import com.example.moviecatalog.data.source.local.entity.TvEntity
import com.example.moviecatalog.databinding.ItemsTvBinding
import com.example.moviecatalog.ui.detail.DetailActivity

class TvAdapter : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
    private var listTv = ArrayList<TvEntity>()

    fun setTvs(tvs: List<TvEntity>) {
        this.listTv.clear()
        this.listTv.addAll(tvs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemsTvBinding =
            ItemsTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemsTvBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tvs = listTv[position]
        holder.bind(tvs)
    }

    override fun getItemCount(): Int {
        return listTv.size
    }

    class TvViewHolder(private val binding: ItemsTvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            val basePoster = "https://image.tmdb.org/t/p/w500"
            with(binding) {
                tvTitle.text = tv.title
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_CONTENT, tv.tvId)
                    intent.putExtra(DetailActivity.EXTRA_TAG, 1)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(basePoster + tv.poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(tvPoster)
            }
        }
    }
}
