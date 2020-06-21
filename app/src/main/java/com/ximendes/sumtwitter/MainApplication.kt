package com.ximendes.sumtwitter

import android.app.Application
import com.ximendes.sumtwitter.di.apiModule
import com.ximendes.sumtwitter.di.appModule
import com.ximendes.sumtwitter.di.repositoryModule
import com.ximendes.sumtwitter.di.retrofitModule
import com.ximendes.sumtwitter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(viewModelModule, appModule, repositoryModule, apiModule, retrofitModule))
        }
    }
}
