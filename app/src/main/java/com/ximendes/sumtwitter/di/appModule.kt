package com.ximendes.sumtwitter.di

import android.content.Context
import com.ximendes.sumtwitter.MainApplication
import com.ximendes.sumtwitter.util.resourceprovider.ResourceProvider
import com.ximendes.sumtwitter.util.resourceprovider.ResourceProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    fun provideResourceProvider(context: Context): ResourceProvider = ResourceProviderImpl(context)

    single { MainApplication() }
    single { provideResourceProvider(androidContext()) }
}
