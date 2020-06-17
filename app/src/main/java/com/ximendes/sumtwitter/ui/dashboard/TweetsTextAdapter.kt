package com.ximendes.sumtwitter.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.databinding.ItemTweetTextBinding

class TweetsTextAdapter(
    private val tweets: List<String>
) :
    RecyclerView.Adapter<TweetsTextAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTweetTextBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tweets[position])

    override fun getItemCount() = tweets.size

    inner class ViewHolder(private val binding: ItemTweetTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tweet: String) {
            binding.tweet = tweet
        }
    }
}