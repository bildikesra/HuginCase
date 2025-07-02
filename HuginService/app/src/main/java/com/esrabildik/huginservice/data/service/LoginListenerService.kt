package com.esrabildik.huginservice.data.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.esrabildik.huginservice.data.broadcast.LoginBroadcastReceiver
import com.esrabildik.huginservice.data.local.dao.LoginDAO
import com.esrabildik.huginservice.data.local.entity.LoginUser
import com.esrabildik.huginservice.data.local.repository.ServiceRepository
import com.esrabildik.huginservice.data.utils.BROADCAST_ACTION
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

    private lateinit var receiver: LoginBroadcastReceiver

    override fun onCreate() {
        super.onCreate()

        receiver = LoginBroadcastReceiver(loginDao)

        val filter = IntentFilter(BROADCAST_ACTION)
        val flags =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Context.RECEIVER_NOT_EXPORTED
            } else {
                ContextCompat.RECEIVER_NOT_EXPORTED
            }
        registerReceiver(receiver, filter, flags)

        val notification = NotificationUtils.buildNotification(this)
        startForeground(NOTIFICATION_ID, notification)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
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
                        Log.d("GetAllUser","${loginDao.getAllLogins()}")
                        val successIntent = Intent(BROADCAST_ACTION).apply {
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

    override fun onBind(intent: Intent?): IBinder? = null
}
