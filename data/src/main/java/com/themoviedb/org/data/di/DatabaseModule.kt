package com.themoviedb.org.data.di

import androidx.room.Room
import com.themoviedb.org.data.data_source.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// DI room database
val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movies-database"
        ).build()
    }

    single { get<AppDatabase>().moviesDao() }

}