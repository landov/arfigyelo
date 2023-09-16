package hu.landov.arfigyelo.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.landov.arfigyelo.data.WatcherRepository
import hu.landov.arfigyelo.data.WatcherRepositoryImpl
import hu.landov.arfigyelo.data.local.WatcherDao
import hu.landov.arfigyelo.data.local.WatcherDb
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideRoomDao(database : WatcherDb) : WatcherDao{
        return database.dao
    }

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context) : WatcherDb{
        return Room.databaseBuilder(context, WatcherDb::class.java,"watcher_database").fallbackToDestructiveMigration().build()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindRepository(watcherRepository: WatcherRepositoryImpl): WatcherRepository
    }

}