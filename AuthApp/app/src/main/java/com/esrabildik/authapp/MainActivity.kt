package com.esrabildik.authapp


import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.esrabildik.authapp.ui.theme.AuthAppTheme
import com.esrabildik.authapp.utils.INTENT_ACTION
import com.esrabildik.authapp.utils.SERVICE_START_ACTION
import com.esrabildik.data.broadcast.LoginResultReceiver
import com.esrabildik.feature.main.Main
import com.esrabildik.feature.util.LOGIN_SAVE_RESULT
import dagger.hilt.android.AndroidEntryPoint

val package_name = "com.esrabildik.productapp"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var loginResultReceiver: LoginResultReceiver


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val serviceIntent = Intent(SERVICE_START_ACTION).apply {
            `package` = "com.esrabildik.huginservice"
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                loginResultReceiver,
                IntentFilter(LOGIN_SAVE_RESULT),
                RECEIVER_EXPORTED
            )
        }

        // TODO: Broadcast al true ise diğer appe geç
        setContent {
            Main(
                startService = { user ->
                    try {
                        Log.d("startService", "Service")
                        serviceIntent.apply {
                            `package` = "com.esrabildik.huginservice"
                            putExtra("user_email", user.email)
                            putExtra("user_id", user.userId)

                        }

                        startForegroundService(serviceIntent)
                    } catch (e: Exception) {
                        e.localizedMessage?.let { Log.e("ServiceException", it) }
                    }

                }

            )

        }

        loginResultReceiver = LoginResultReceiver { success ->
            Log.d("isSucces", "$success")
            if (success) {
                // TODO: ikinci app'e geçiş işlemi buraya yazılır
                Log.d("LoginReceiver", "Login başarılı, diğer uygulamaya geçiliyor")

                val startProductAppIntent = Intent().apply {
                    component =
                        ComponentName(package_name, "com.esrabildik.productapp.MainActivity")
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

                }
                startActivity(startProductAppIntent)

                finish()
            } else {
                Log.d("LoginReceiver", "Login başarısız")
            }
        }

        val filter = IntentFilter(INTENT_ACTION)
        registerReceiver(loginResultReceiver, filter, RECEIVER_EXPORTED)

    }

    override fun onDestroy() {
        super.onDestroy()
        //Receiver'ı kaldır
        unregisterReceiver(loginResultReceiver)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AuthAppTheme {

    }
}