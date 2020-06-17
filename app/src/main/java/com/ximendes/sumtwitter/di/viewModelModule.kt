package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.ui.dashboard.DashboardViewModel
import com.ximendes.sumtwitter.ui.home.HomeViewModel
import com.ximendes.sumtwitter.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
}