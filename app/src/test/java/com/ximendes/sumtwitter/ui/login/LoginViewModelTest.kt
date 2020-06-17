package com.ximendes.sumtwitter.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ximendes.sumtwitter.data.repository.login.LoginRepository
import com.ximendes.sumtwitter.util.resourceprovider.ResourceProvider
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: LoginRepository

    @MockK
    private lateinit var resourceProvider: ResourceProvider

    private val loginSuccessObserver = mockk<Observer<Unit>>(relaxed = true)

    private val errorMessageObserver = mockk<Observer<String>>(relaxed = true)

    @InjectMockKs
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun `when user click on login button should check pending task`() {
    }

    @Test
    fun `when have no pending task should start sign in flow`() {
    }

    @Test
    fun `when have pending task should proceed to timeline`() {
    }

    @Test
    fun `when login be succeed should proceed to timeline`() {
    }

    @Test
    fun `when login fail should show an error message`() {
    }
}
