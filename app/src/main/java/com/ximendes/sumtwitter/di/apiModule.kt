package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.data.api.HomeApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideTimelineApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }

    single { provideTimelineApi(get()) }

}