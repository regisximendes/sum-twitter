package com.ximendes.sumtwitter.di

import com.ximendes.sumtwitter.MainApplication
import org.koin.dsl.module

val appModule = module {

    single { MainApplication() }
}