package com.themoviedb.org.app

import android.app.Application

import com.themoviedb.org.data.di.databaseModule
import com.themoviedb.org.data.di.networkModule

import com.themoviedb.org.di.appModule
import com.themoviedb.org.di.dataModule
import com.themoviedb.org.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MoviesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // included modules
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MoviesApplication)
            modules(listOf(appModule, domainModule, dataModule, networkModule, databaseModule))
        }

    }

}