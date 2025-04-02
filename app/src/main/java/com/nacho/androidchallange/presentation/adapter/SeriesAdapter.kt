package com.nacho.androidchallange.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nacho.androidchallange.core.ImageLoader
import com.nacho.androidchallange.data.model.Serie
import com.nacho.androidchallange.databinding.ItemSerieBinding

class SeriesAdapter(
    private var seriesList: List<Serie>,
    private val onItemClick: (Serie) -> Unit
) : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = ItemSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val serie = seriesList[position]
        holder.bind(serie)
        holder.itemView.setOnClickListener { onItemClick(serie) }
    }

    override fun getItemCount(): Int = seriesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Serie>) {
        seriesList = newList
        notifyDataSetChanged()
    }

    class SeriesViewHolder(private val binding: ItemSerieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Serie) {
            with(binding) {
                itemSerieTitle.text = item.name
                itemSerieDescription.text = item.summary

                ImageLoader.loadImage(
                    itemView.context,
                    item.image?.original,
                    itemSerieImg,
                    onLoadFailed = {
                        itemSerieImg.visibility = View.GONE
                    },
                    onResourceReady = {}
                )
            }
        }
    }

}