package com.esrabildik.huginservice.data.local.repository

import com.esrabildik.huginservice.data.local.dao.LoginDAO
import com.esrabildik.huginservice.data.local.entity.LoginUser
import javax.inject.Inject


class ServiceRepository @Inject constructor(
    private val  loginDAO: LoginDAO
){
    suspend fun getAllUsers() : List<LoginUser>{
        return loginDAO.getAllLogins()
    }

    suspend fun insertUser(loginUser :LoginUser) : Boolean{
        return try {
            val existingUser = loginDAO.getUserByEmail(loginUser.userEmail)
            if (existingUser == null){
                loginDAO.insertLogin(loginUser)
            }
            true
        }catch (e : Exception){
            false
        }

    }


}