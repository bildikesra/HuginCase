package com.esrabildik.di.RoomModule

import android.content.Context
import androidx.room.Room
import com.esrabildik.data.local.dao.CardDao
import com.esrabildik.data.local.database.AppDatabase
import com.esrabildik.domain.repository.CardRepository
import com.esrabildik.repository.CardRepositoryImpl
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCardDao(db: AppDatabase): CardDao {
        return db.cardDao()
    }

    @Provides
    @Singleton
    fun provideCardRepository(dao: CardDao): CardRepository {
        return CardRepositoryImpl(dao)
    }
}
