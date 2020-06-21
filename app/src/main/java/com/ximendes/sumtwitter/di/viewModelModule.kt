package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.ui.home.HomeViewModel
import com.ximendes.sumtwitter.ui.login.LoginViewModel
import com.ximendes.sumtwitter.ui.profile.ProfiledViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProfiledViewModel(get(), get()) }
}
