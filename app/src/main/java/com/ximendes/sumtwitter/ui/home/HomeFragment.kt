package com.ximendes.sumtwitter.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ximendes.sumtwitter.R
import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.databinding.FragmentHomeBinding
import com.ximendes.sumtwitter.ui.login.LoginActivity
import com.ximendes.sumtwitter.util.constants.Constants.USER_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), TweetListener {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TweetsAdapter
    private lateinit var navController: NavController

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
        setupNavController(view)
        viewModel.getUserTimeline()
    }

    private fun setupNavController(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun observeViewModel() = with(viewModel) {
        tweets.observe(viewLifecycleOwner, Observer { tweets ->
            setupTweetList(tweets)
        })

        errorEvent.observe(viewLifecycleOwner, Observer {
            showErrorState()
        })

        signOutEvent.observe(viewLifecycleOwner, Observer {
            navigateToLogin()
        })
    }

    private fun setupTweetList(tweets: List<Tweet>) {
        this.adapter = TweetsAdapter(this, tweets)
        binding.tweetsRecyclerView.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
        }
    }

    override fun onTweetClicked(userName: String) {
        navController.navigate(
            R.id.action_navigation_home_to_navigation_dashboard,
            bundleOf(USER_NAME to userName)
        )
    }

    private fun showErrorState() {
        binding.tweetsRecyclerView.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
    }

    private fun navigateToLogin() {
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }
}
