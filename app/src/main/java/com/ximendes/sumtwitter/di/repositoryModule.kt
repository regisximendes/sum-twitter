package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.data.repository.timeline.LoginRepository
import com.ximendes.sumtwitter.data.repository.timeline.LoginRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideLoginRepository(): LoginRepository = LoginRepositoryImpl()

    single { provideLoginRepository() }

}