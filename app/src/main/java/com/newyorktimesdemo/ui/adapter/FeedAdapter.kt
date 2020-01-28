package com.newyorktimesdemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newyorktimesdemo.R
import com.newyorktimesdemo.model.Results
import com.newyorktimesdemo.base.BaseViewHolder
import com.newyorktimesdemo.databinding.AdapterFeedItemBinding
import com.newyorktimesdemo.ui.activity.RecyclerViewClickListener


class FeedAdapter(var listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<FeedViewHolder>() {
    var feedRow: List<Results> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            AdapterFeedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return feedRow!!.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.onBind(feedRow!![position])
        holder.itemView.setOnClickListener { listener.itemClicked(position) }
    }


    fun addAll(rows: ArrayList<Results>) {
        if (rows !== feedRow) {
            feedRow = rows
            notifyDataSetChanged()
        }
    }
}

class FeedViewHolder(private val binding: AdapterFeedItemBinding) :
    BaseViewHolder(binding) {
    fun onBind(feed: Results?) {
        binding.model = feed
        Glide.with(binding.ivLogo.context).load(feed?.media?.get(0)?.mediametadata?.get(0)?.url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.ivLogo)
        binding.executePendingBindings()
    }
}