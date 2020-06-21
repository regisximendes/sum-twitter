package com.ximendes.sumtwitter.di

import android.content.Context
import com.ximendes.sumtwitter.data.api.TimeLinesApi
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import com.ximendes.sumtwitter.data.repository.home.HomeRepositoryImpl
import com.ximendes.sumtwitter.data.repository.login.LoginRepository
import com.ximendes.sumtwitter.data.repository.login.LoginRepositoryImpl
import com.ximendes.sumtwitter.data.repository.user.UserRepository
import com.ximendes.sumtwitter.data.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideHomeRepository(timeLinesApi: TimeLinesApi): HomeRepository =
        HomeRepositoryImpl(timeLinesApi)

    fun provideUserRepository(context: Context): UserRepository = UserRepositoryImpl(context)

    single { provideHomeRepository(get()) }
    single { provideUserRepository(get()) }
}
