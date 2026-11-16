package com.birdwastemanagement.app.di

import android.content.Context
import androidx.room.Room
import com.birdwastemanagement.app.data.local.AppDatabase
import com.birdwastemanagement.app.data.local.CustomerDao
import com.birdwastemanagement.app.data.repository.WasteRepositoryImpl
import com.birdwastemanagement.app.domain.repository.WasteRepository
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bird_waste_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCustomerDao(database: AppDatabase): CustomerDao {
        return database.customerDao()
    }

    @Provides
    @Singleton
    fun provideWasteRepository(repositoryImpl: WasteRepositoryImpl): WasteRepository {
        return repositoryImpl
    }
}
