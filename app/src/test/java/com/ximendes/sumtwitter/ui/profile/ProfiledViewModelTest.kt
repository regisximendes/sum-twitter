package com.ximendes.sumtwitter.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ximendes.sumtwitter.data.mapper.toTweet
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
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfiledViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var timeLineRepository: TimeLineRepository

    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var viewModel: ProfiledViewModel

    private val tweetsRequest = TweetsRequest("1", "1")

    private val userResponse = UserResponse("1", "test", "test", "test", "test")

    private val tweetResponseList = listOf(TweetResponse("1", "tweet", userResponse))

    private val errorObserver = mockk<Observer<Unit>>(relaxed = true)

    private val fullNameObserver = mockk<Observer<String>>(relaxed = true)

    private val userNameObserver = mockk<Observer<String>>(relaxed = true)

    private val descriptionObserver = mockk<Observer<String>>(relaxed = true)

    private val profileImageUrlObserver = mockk<Observer<String>>(relaxed = true)

    private val isLoadingObserver = mockk<Observer<Boolean>>(relaxed = true)

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = ProfiledViewModel(timeLineRepository, userRepository)
    }

    @Test
    fun `when view model start should fetch user tweets list`() {
        every { timeLineRepository.getUserHome("test", tweetsRequest) } returns Single.just(
            tweetResponseList
        )

        val testObserver = TestObserver<List<TweetResponse>>()
        val result = timeLineRepository.getUserHome("test", tweetsRequest)

        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val tweets = testObserver.values()[0]
        MatcherAssert.assertThat(tweets.size, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(tweets[0].id, CoreMatchers.`is`("1"))
        MatcherAssert.assertThat(tweets[0].text, CoreMatchers.`is`("tweet"))
        Assert.assertNotNull(tweets[0].user)
    }

    @Test
    fun `when fetch tweets fail should show an error`() {
        val error = Exception("Error")
        viewModel.errorEvent.observeForever(errorObserver)
        every { timeLineRepository.getUserHome("test", tweetsRequest) } returns Single.error(error)

        val testObserver = TestObserver<List<TweetResponse>>()
        val result =
            timeLineRepository.getUserHome("test", tweetsRequest)
                .doOnError { viewModel.errorEvent.call() }

        result.subscribe(testObserver)
        testObserver.assertError(error)

        verify { errorObserver.onChanged(any()) }
    }

    @Test
    fun `when view model start should set user data`() {
        viewModel.fullName.observeForever(fullNameObserver)
        viewModel.userName.observeForever(userNameObserver)
        viewModel.description.observeForever(descriptionObserver)
        viewModel.profileImageUrl.observeForever(profileImageUrlObserver)

        every { timeLineRepository.getUserHome("test", tweetsRequest) } returns Single.just(
            tweetResponseList
        )

        val testObserver = TestObserver<List<TweetResponse>>()
        val result = timeLineRepository.getUserHome("test", tweetsRequest).doOnSuccess {
            val user = it.firstOrNull()?.toTweet()
            viewModel.fullName.value = user?.fullName
            viewModel.userName.value = user?.userName
            viewModel.description.value = user?.description
            viewModel.profileImageUrl.value = user?.profileImageUrl
        }

        result.subscribe(testObserver)

        verify { fullNameObserver.onChanged("test") }
        verify { userNameObserver.onChanged("@test") }
        verify { descriptionObserver.onChanged("test") }
        verify { profileImageUrlObserver.onChanged("test") }
    }

    @Test
    fun `when start fetch user home should show the progress bar`() {
        viewModel.isLoading.observeForever(isLoadingObserver)

        viewModel.getUserHome("test")

        verify { isLoadingObserver.onChanged(true) }
    }

    @Test
    fun `when finish fetch user home should hide progress bar`() {
        viewModel.isLoading.observeForever(isLoadingObserver)
        every { timeLineRepository.getUserHome("test", tweetsRequest) } returns Single.just(
            tweetResponseList
        )

        val testObserver = TestObserver<List<TweetResponse>>()
        val result = timeLineRepository.getUserHome("test", tweetsRequest)
            .doAfterTerminate { viewModel.isLoading.value = false }

        result.subscribe(testObserver)

        verify { isLoadingObserver.onChanged(false) }
    }
}
