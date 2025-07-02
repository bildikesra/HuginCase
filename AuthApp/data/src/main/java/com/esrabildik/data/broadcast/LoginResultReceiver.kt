package com.esrabildik.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class LoginResultReceiver(
    val onResult: (Boolean) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent : Intent?) {

        val isInserted = intent?.getBooleanExtra("IsInserted",false) ?: false
        Log.d("LoginResultReceiver","$isInserted")
        onResult(isInserted)
    }
}