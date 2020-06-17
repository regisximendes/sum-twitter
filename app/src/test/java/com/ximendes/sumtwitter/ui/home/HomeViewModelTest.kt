package com.ximendes.sumtwitter.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: HomeRepository

    @InjectMockKs
    private lateinit var viewModel: HomeViewModel

    private val tweetsObserver = mockk<Observer<List<Tweet>>>(relaxed = true)

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `should call repository`() {
        every { repository.getUserTimeline() } returns Single.just(emptyList())
        viewModel.getUserTimeline()

        verify { repository.getUserTimeline() }
    }
}
