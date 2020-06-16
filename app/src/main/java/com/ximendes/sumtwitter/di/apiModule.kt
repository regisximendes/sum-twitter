package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.data.api.TimelineApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideTimelineApi(retrofit: Retrofit): TimelineApi {
        return retrofit.create(TimelineApi::class.java)
    }

    single { provideTimelineApi(get()) }

}