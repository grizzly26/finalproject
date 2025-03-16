package com.example.finalproject.di

import android.app.Application
import androidx.room.Room
import com.example.finalproject.db.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    // todo Указывает, в какой компонент Dagger Hilt нужно устанавливать
    //  зависимости из этого модуля.
    //   SingletonComponent означает, что зависимости будут
    //   доступны во всем жизненном цикле приложения

    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb{
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "info.db"
        ).createFromAsset("db/info.db").build()
    }
}