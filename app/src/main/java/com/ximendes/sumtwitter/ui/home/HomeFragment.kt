package com.ximendes.sumtwitter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ximendes.sumtwitter.R
import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TweetsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return setupViewBinding(inflater, container)
    }

    private fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        tweets.observe(viewLifecycleOwner, Observer { tweets ->
            setupTweetList(tweets)
        })
    }

    private fun paginatedTweets(tweets: List<Tweet>) {
        adapter.addTweetsToList(tweets)
    }

    private fun setupTweetList(tweets: List<Tweet>) {
        this.adapter = TweetsAdapter()
        binding.tweetsRecyclerView.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
        }
        adapter.addTweetsToList(tweets)
    }
}
