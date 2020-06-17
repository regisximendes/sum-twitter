package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.data.api.TimeLinesApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideTimelineApi(retrofit: Retrofit): TimeLinesApi {
        return retrofit.create(TimeLinesApi::class.java)
    }

    single { provideTimelineApi(get()) }
}
