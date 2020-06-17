package com.ximendes.sumtwitter.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.databinding.ItemTweetBinding

class TweetsAdapter(
    private val listener: TweetListener,
    private val tweets: List<Tweet>
) :
    RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTweetBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tweets[position])

    override fun getItemCount() = tweets.size

    inner class ViewHolder(private val binding: ItemTweetBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(tweet: Tweet) {
            binding.data = tweet
        }

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            listener.onTweetClicked(tweets[adapterPosition].userName)
        }
    }
}
