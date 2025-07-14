package com.esrabildik.huginservice.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esrabildik.huginservice.data.local.entity.LoginUser

@Dao
interface LoginDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogin(loginEnum: LoginUser) : Long


    @Query("SELECT * FROM login_table")
    suspend fun getAllLogins() : List<LoginUser>

    @Query("SELECT * FROM login_table WHERE userEmail = :email LIMIT 1")
    suspend fun getUserByEmail(email: String?): LoginUser?

}