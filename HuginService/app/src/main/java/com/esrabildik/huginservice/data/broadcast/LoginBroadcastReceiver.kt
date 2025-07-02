package com.esrabildik.huginservice.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.esrabildik.huginservice.data.local.dao.LoginDAO
import com.esrabildik.huginservice.data.local.entity.LoginUser
import com.esrabildik.huginservice.data.utils.LOGIN_SAVE_RESULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginBroadcastReceiver @Inject constructor(
    private val loginDAO: LoginDAO
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val userEmail = intent?.getStringExtra("user_email") ?: return

        CoroutineScope(Dispatchers.IO).launch {
            val existingUser = loginDAO.getUserByEmail(userEmail)
            val isSuccess: Boolean

            val userList = loginDAO.getAllLogins()
            if (userList.isEmpty()) {
                Log.d("userList", "Null veya boş")
            } else {
                Log.d("userList", "Null değil - Liste dolu: ${userList.size} kayıt var")
            }



            // Geriye broadcast gönderelim
            val intent = Intent(LOGIN_SAVE_RESULT)
            val bundle = Bundle()
          //  bundle.putBoolean("save_success", isSuccess)
            bundle.putString("user_email", userEmail)
            intent.putExtras(bundle)
            context?.sendBroadcast(intent)

        }
    }
}