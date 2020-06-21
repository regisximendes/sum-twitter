package com.ximendes.sumtwitter.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ximendes.sumtwitter.data.repository.user.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: UserRepository

    private val signInFlowObserver = mockk<Observer<Unit>>(relaxed = true)

    private val timeLineNavigationObserver = mockk<Observer<Unit>>(relaxed = true)

    private val errorObserver = mockk<Observer<Unit>>(relaxed = true)

    private val isLoadingObserver = mockk<Observer<Boolean>>(relaxed = true)

    @InjectMockKs
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun `when user click on login button should start login flow`() {
        viewModel.signInFlowEvent.observeForever(signInFlowObserver)
        viewModel.startLoginFlow()

        verify { signInFlowObserver.onChanged(any()) }
    }

    @Test
    fun `when login be succeed should proceed to timeline`() {
        viewModel.timeLineNavigationEvent.observeForever(timeLineNavigationObserver)
        viewModel.loginSuccess()

        verify { timeLineNavigationObserver.onChanged(any()) }
    }

    @Test
    fun `when login fail should show an error`() {
        viewModel.errorEvent.observeForever(errorObserver)
        viewModel.loginFail()

        verify { errorObserver.onChanged(any()) }
    }

    @Test
    fun `when view model start should check user session`() {
        verify { repository.hasUserLogged() }
    }

    @Test
    fun `whe has user logged should navigate to time line`() {
        every { repository.hasUserLogged() } returns true

        viewModel.signInFlowEvent.observeForever(signInFlowObserver)
        viewModel.startLoginFlow()

        verify { signInFlowObserver.onChanged(any()) }
    }

    @Test
    fun `when login flow start should show the progress bar`() {
        viewModel.isLoading.observeForever(isLoadingObserver)
        viewModel.startLoginFlow()

        verify { isLoadingObserver.onChanged(true) }
    }

    @Test
    fun `when login  be succeed should hide the progress bar`() {
        viewModel.isLoading.observeForever(isLoadingObserver)
        viewModel.loginSuccess()

        verify { isLoadingObserver.onChanged(false) }
    }

    @Test
    fun `when login fail should hide the progress bar`() {
        viewModel.isLoading.observeForever(isLoadingObserver)
        viewModel.loginFail()

        verify { isLoadingObserver.onChanged(false) }
    }
}
