package com.esrabildik.huginservice.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_table")
data class LoginUser(
    @PrimaryKey
    val userID: String,
    val userEmail: String,
)
