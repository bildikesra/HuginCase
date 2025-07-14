package com.esrabildik.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esrabildik.data.local.dao.CardDao
import com.esrabildik.data.local.entity.CardItemEntity

@Database(entities = [CardItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun cardDao() : CardDao

}