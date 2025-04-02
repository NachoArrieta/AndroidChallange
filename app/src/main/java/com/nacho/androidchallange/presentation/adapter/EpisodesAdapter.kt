package com.nacho.androidchallange.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nacho.androidchallange.core.ImageLoader
import com.nacho.androidchallange.data.model.Episode
import com.nacho.androidchallange.databinding.ItemEpisodeBinding

class EpisodesAdapter(private var episodeList: List<Episode>) :
    RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val episode = episodeList[position]
        holder.bind(episode)
    }

    override fun getItemCount(): Int = episodeList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Episode>) {
        episodeList = newList
        notifyDataSetChanged()
    }

    class EpisodesViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            with(binding) {
                itemDetailTitle.text = episode.name

                ImageLoader.loadImage(
                    itemView.context,
                    episode.image?.medium,
                    itemDetailImg,
                    onLoadFailed = { itemDetailImg.visibility = View.GONE },
                    onResourceReady = {}
                )
            }
        }
    }

}