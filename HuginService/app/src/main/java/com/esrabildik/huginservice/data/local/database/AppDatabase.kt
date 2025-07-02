package com.esrabildik.huginservice.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esrabildik.huginservice.data.local.dao.LoginDAO
import com.esrabildik.huginservice.data.local.entity.LoginUser

@Database(entities = [LoginUser::class], version = 1)
abstract class AppDatabase  : RoomDatabase(){
    abstract fun loginDao() : LoginDAO
}