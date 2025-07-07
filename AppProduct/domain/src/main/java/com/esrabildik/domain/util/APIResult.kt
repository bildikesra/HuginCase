package com.esrabildik.domain.util

import android.os.Message



sealed class APIResult<out R> {

    data class Success<out R>( val data : R) : APIResult<R>()
    data class Error(val message : String) : APIResult<Nothing>()
    data object Loading : APIResult<Nothing>()

}