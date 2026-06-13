package com.juanjojmnz.gtavguide.di

import android.content.Context
import com.juanjojmnz.gtavguide.data.db.AppDatabase
import com.juanjojmnz.gtavguide.data.db.MissionDao
import com.juanjojmnz.gtavguide.data.db.PropertyDao
import com.juanjojmnz.gtavguide.data.db.RandomEventDao
import com.juanjojmnz.gtavguide.data.repository.MissionRepository
import com.juanjojmnz.gtavguide.data.repository.PropertyRepository
import com.juanjojmnz.gtavguide.data.repository.RandomEventRepository
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

    @Provides
    @Singleton
    fun providePropertyDao(db: AppDatabase): PropertyDao =
        db.propertyDao()

    @Provides
    @Singleton
    fun providePropertyRepository(dao: PropertyDao): PropertyRepository =
        PropertyRepository(dao)

    @Provides
    @Singleton
    fun provideRandomEventDao(db: AppDatabase): RandomEventDao =
        db.randomEventDao()

    @Provides
    @Singleton
    fun provideRandomEventRepository(dao: RandomEventDao): RandomEventRepository =
        RandomEventRepository(dao)
}