package com.ximendes.sumtwitter.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ximendes.sumtwitter.R
import com.ximendes.sumtwitter.databinding.FragmentDashboardBinding
import com.ximendes.sumtwitter.util.constants.Constants.USER_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModel()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var adapter: TweetsTextAdapter

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
            R.layout.fragment_dashboard,
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
        val userName = arguments?.getString(USER_NAME)
        viewModel.getUserHome(userName ?: return)
    }


    private fun observeViewModel() = with(viewModel) {
        tweets.observe(viewLifecycleOwner, Observer { tweets ->
            setupTweetList(tweets)
        })
    }

    private fun setupTweetList(tweets: List<String>) {
        this.adapter = TweetsTextAdapter(tweets)
        binding.tweetsRecyclerView.apply {
            adapter = this@DashboardFragment.adapter
            layoutManager = LinearLayoutManager(this@DashboardFragment.context)
        }
    }

}
