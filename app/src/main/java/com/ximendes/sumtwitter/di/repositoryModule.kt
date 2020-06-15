package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.data.repository.login.LoginRepository
import com.ximendes.sumtwitter.data.repository.login.LoginRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideLoginRepository(): LoginRepository =
        LoginRepositoryImpl()

    single { provideLoginRepository() }

}