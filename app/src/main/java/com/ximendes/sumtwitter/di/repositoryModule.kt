package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.data.api.HomeApi
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import com.ximendes.sumtwitter.data.repository.home.HomeRepositoryImpl
import com.ximendes.sumtwitter.data.repository.login.LoginRepository
import com.ximendes.sumtwitter.data.repository.login.LoginRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideLoginRepository(): LoginRepository = LoginRepositoryImpl()

    fun provideHomeRepository(homeApi: HomeApi): HomeRepository = HomeRepositoryImpl(homeApi)

    single { provideLoginRepository() }
    single { provideHomeRepository(get()) }

}