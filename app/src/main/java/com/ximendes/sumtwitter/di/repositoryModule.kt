package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.data.api.TimeLinesApi
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import com.ximendes.sumtwitter.data.repository.home.HomeRepositoryImpl
import com.ximendes.sumtwitter.data.repository.login.LoginRepository
import com.ximendes.sumtwitter.data.repository.login.LoginRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideLoginRepository(): LoginRepository = LoginRepositoryImpl()

    fun provideHomeRepository(timeLinesApi: TimeLinesApi): HomeRepository = HomeRepositoryImpl(timeLinesApi)

    single { provideLoginRepository() }
    single { provideHomeRepository(get()) }
}
