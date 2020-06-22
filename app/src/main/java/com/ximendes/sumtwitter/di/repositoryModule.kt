package com.ximendes.sumtwitter.di

import android.content.Context
import com.ximendes.sumtwitter.data.api.TimeLinesApi
import com.ximendes.sumtwitter.data.repository.home.TimeLineRepository
import com.ximendes.sumtwitter.data.repository.home.TimeLineRepositoryImpl
import com.ximendes.sumtwitter.data.repository.user.UserRepository
import com.ximendes.sumtwitter.data.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideTimeLineRepository(timeLinesApi: TimeLinesApi): TimeLineRepository =
        TimeLineRepositoryImpl(timeLinesApi)

    fun provideUserRepository(context: Context): UserRepository = UserRepositoryImpl(context)

    single { provideTimeLineRepository(get()) }
    single { provideUserRepository(get()) }
}
