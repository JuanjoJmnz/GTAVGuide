package com.juanjojmnz.gtavguide.di

import android.content.Context
import com.juanjojmnz.gtavguide.data.db.AppDatabase
import com.juanjojmnz.gtavguide.data.db.MissionDao
import com.juanjojmnz.gtavguide.data.repository.MissionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideMissionDao(db: AppDatabase): MissionDao =
        db.missionDao()

    @Provides
    @Singleton
    fun provideMissionRepository(dao: MissionDao): MissionRepository =
        MissionRepository(dao)
}