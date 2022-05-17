package net.carfax.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.carfax.room.AppDatabase
import net.carfax.room.AssignmentDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "CarFaxDB"
        ).build()
    }

    @Provides
    fun provideAssignmentDao(appDatabase: AppDatabase): AssignmentDao {
        return appDatabase.locationDao()
    }


}