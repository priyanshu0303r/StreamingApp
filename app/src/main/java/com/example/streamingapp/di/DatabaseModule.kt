package com.example.streamingapp.di

import android.content.Context
import androidx.room.Room
import com.example.streamingapp.data.local.AppDatabase
import com.example.streamingapp.data.local.StreamingDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "streaming_app_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideStreamingDataDao(appDatabase: AppDatabase): StreamingDataDao =
        appDatabase.streamingDataDao()
}
