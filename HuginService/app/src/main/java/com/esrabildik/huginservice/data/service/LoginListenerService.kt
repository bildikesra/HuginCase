package com.esrabildik.huginservice.data.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.esrabildik.huginservice.data.local.dao.LoginDAO
import com.esrabildik.huginservice.data.local.entity.LoginUser
import com.esrabildik.huginservice.data.local.repository.ServiceRepository
import com.esrabildik.huginservice.data.utils.INTENT_ACTION
import com.esrabildik.huginservice.data.utils.NOTIFICATION_ID
import com.esrabildik.huginservice.data.utils.NotificationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginListenerService : Service() {

    @Inject
    lateinit var serviceRepository: ServiceRepository

    @Inject
    lateinit var loginDao: LoginDAO


    override fun onCreate() {
        super.onCreate()

        val notification = NotificationUtils.buildNotification(this)
        startForeground(NOTIFICATION_ID, notification)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("onStartCommand", "onStartCommand calisti")

        CoroutineScope(Dispatchers.IO).launch {
            val email = intent?.getStringExtra("user_email")
            val id = intent?.getStringExtra("user_id")

            if (!email.isNullOrEmpty() && !id.isNullOrEmpty()) {
                val loginUser = LoginUser(id, email)

                try {
                    val isInserted = serviceRepository.insertUser(loginUser)
                    if (isInserted) {
                        Log.d("IsInserted", "true")
                        Log.d("GetAllUser","${serviceRepository.getAllUsers()}")
                        val successIntent = Intent(INTENT_ACTION).apply {
                            putExtra("IsInserted",true)
                        }
                        sendBroadcast(successIntent)
                    } else {
                        Log.e("Database error", "Kayıt başarısız")
                    }
                } catch (e: Exception) {
                    Log.e("Insert Error", "Exception: ${e.localizedMessage}")
                }
            } else {
                Log.e("Intent Error", "Email veya ID null veya boş")
            }
            stopSelf()
        }


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
