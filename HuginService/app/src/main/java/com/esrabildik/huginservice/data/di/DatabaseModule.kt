package com.esrabildik.huginservice.data.di

import android.content.Context
import androidx.room.Room
import com.esrabildik.huginservice.data.local.dao.LoginDAO
import com.esrabildik.huginservice.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context : Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "login_db"
        ).build()
    }

    @Provides
    fun provideLoginDao( db : AppDatabase)
    : LoginDAO = db.loginDao()
}