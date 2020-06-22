package com.ximendes.sumtwitter.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ximendes.sumtwitter.data.repository.home.TimeLineRepository
import com.ximendes.sumtwitter.data.repository.user.UserRepository
import com.ximendes.sumtwitter.data.request.TweetsRequest
import com.ximendes.sumtwitter.data.response.TweetResponse
import com.ximendes.sumtwitter.data.response.UserResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var timeLineRepository: TimeLineRepository

    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var viewModel: HomeViewModel

    private val signOutObserver = mockk<Observer<Unit>>(relaxed = true)

    private val tweetsRequest = TweetsRequest("1", "1")

    private val userResponse = UserResponse("1", "", "", "", "")

    private val tweetResponseList = listOf(TweetResponse("1", "tweet", userResponse))

    private val isLoadingObserver = mockk<Observer<Boolean>>(relaxed = true)

    private val errorObserver = mockk<Observer<Unit>>(relaxed = true)

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = HomeViewModel(timeLineRepository, userRepository)
    }

    @Test
    fun `when view model start should fetch a tweet list`() {
        every { timeLineRepository.getUserTimeline(tweetsRequest) } returns Single.just(
            tweetResponseList
        )

        val testObserver = TestObserver<List<TweetResponse>>()
        val result = timeLineRepository.getUserTimeline(tweetsRequest)

        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val tweets = testObserver.values()[0]
        assertThat(tweets.size, `is`(1))
        assertThat(tweets[0].id, `is`("1"))
        assertThat(tweets[0].text, `is`("tweet"))
        Assert.assertNotNull(tweets[0].user)
    }

    @Test
    fun `when sign out button clicked should logout`() {
        viewModel.signOutEvent.observeForever(signOutObserver)
        viewModel.logout()

        verify { userRepository.logout() }
        verify { signOutObserver.onChanged(any()) }
    }

    @Test
    fun `when fetch tweets fail should show an error`() {
        val error = Exception("Error")
        viewModel.errorEvent.observeForever(errorObserver)
        every { timeLineRepository.getUserTimeline(tweetsRequest) } returns Single.error(error)

        val testObserver = TestObserver<List<TweetResponse>>()
        val result =
            timeLineRepository.getUserTimeline(tweetsRequest).doOnError { viewModel.errorEvent.call() }

        result.subscribe(testObserver)
        testObserver.assertError(error)

        verify { errorObserver.onChanged(any()) }
    }

    @Test
    fun `when start fetch tweets should show the progress bar`() {
        viewModel.isLoading.observeForever(isLoadingObserver)

        viewModel.getUserTimeline()

        verify { isLoadingObserver.onChanged(true) }
    }

    @Test
    fun `when finish fetch tweets should hide progress bar`() {
        viewModel.isLoading.observeForever(isLoadingObserver)
        every { timeLineRepository.getUserTimeline(tweetsRequest) } returns Single.just(
            tweetResponseList
        )

        val testObserver = TestObserver<List<TweetResponse>>()
        val result = timeLineRepository.getUserTimeline(tweetsRequest)
            .doAfterTerminate { viewModel.isLoading.value = false }

        result.subscribe(testObserver)

        verify { isLoadingObserver.onChanged(false) }
    }
}
